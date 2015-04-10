import groovy.io.GroovyPrintStream
import org.slf4j.LoggerFactory
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.http.client.RequestSpec
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class HandlerSpec extends Specification {
  // Start our application and make it available for testing. `@Shared` means the same app instance will be used for _all_ tests
  @Shared ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()

  // ApplicationUnderTest includes a TestHttpClient that we can use for sending requests to our application.
  @Delegate TestHttpClient testClient = appUnderTest.httpClient

  def "01 - can send a request to the root path"() {
    when: "a GET request is sent with no path"
    testClient.get() // we don't have to assign the ReceivedResponse returned as TestHttpClient will keep track of this for us

    then: "a response is returned with body text of 'Hello Greach!'"
    testClient.response.body.text == "Hello Greach!" // `testClient.response` is the ReceivedResponse from the last request sent

    /*
    Hint:
    Take a look at `ratpack.groovy.handling.GroovyChain#handler(handler)`
    */
  }

  def "02 - can send a GET request to the path '/user'"() {
    when: "a GET request is sent to the path '/user'"
    get("user") // Using `@Delegate` on the testClient property means we don't have to keep doing `testClient.get()`

    then: "a response is returned with body text of 'user'"
    response.body.text == "user" // Taking advantage of `@Delegate` here too

    /*
    Hint:
    Take a look at `ratpack.groovy.handling.GroovyChain#get(path, handler)`
    A plain `Handler` with no path will match on everything
    */
  }

  @Unroll
  def "03 - can send a GET request to the path '/user/#username'"() {
    expect:
    getText("user/$username") == "user/$username" // Using `getText()` we can roll 2 method calls into 1

    where:
    username << ["dave", "fred"]

    /*
    Hint:
    Take a look at `ratpack.handling.Chain` class level Javadoc
    and `ratpack.handling.Context#getPathTokens()`
    and `ratpack.path.PathBinding#getTokens()`
    */
  }

  @Unroll
  def "04 - can send a GET request to the path '/user/#username/tweets'"() {
    expect:
    getText("user/$username/tweets") == "user/$username/tweets"

    where:
    username << ["dave", "fred"]
  }

  @Unroll
  def "05 - can send a GET request to the path '/user/#username/friends'"() {
    expect:
    getText("user/$username/friends") == "user/$username/friends"

    where:
    username << ["dave", "fred"]

    /*
    Hint:
    If you haven't already, you might want to start refactoring
    Take a look at `ratpack.handling.Chain#prefix(prefix, action)`
    and `ratpack.handling.Context#getAllPathTokens()`
    and `ratpack.path.PathBinding#getAllTokens()`
    */
  }

  def "06 - can send a POST request to the path '/user'"() {
    expect:
    postText("user") == "user"

    /*
    Hint:
    Take a look at `ratpack.groovy.handling.GroovyContext#byMethod(closure)`
    */
  }

  def "07 - can not send a PUT request to the path '/user'"() {
    expect:
    put("user").statusCode == 405
  }

  def "08 - can request a static asset"() {
    expect:
    getText("assets/js/app.js") == "var message = 'Hello Greach!';"

    /*
    Hint:
    There is already a file available to serve src/ratpack/public/js/app.js
    Take a look at `ratpack.handling.Chain#assets(path, indexFiles)`
    */
  }

  def "09 - can serve an index file"() {
    expect:
    getText("home") == "<html><body><p>Hello Greach!</p></body></html>"

    /*
    Hint:
    There is already an index file available to serve src/ratpack/pages/home/index.html
    */
  }

  @Unroll
  def "10 - can log a warning when there is a request for a user starting with 'f'"() {
    // Configure the logger with our own output stream so we can get a handle on the log content
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(HandlerSpec)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get("user/$username/tweets")

    expect:
    loggerOutput.toString().contains("Warning, request for $username")

    where:
    username << ["florence", "fred"]

    /*
    Hint:
    Handlers that don't generate a response must delegate to the next handler
    Take a look at `ratpack.handling.Context#next()`

    Path route matching with a regular expression might help here
    Take a look at `ratpack.handling.Chain` class level Javadoc
    */
  }

  def "11 - can log all requests"() {
    // Configure the logger with our own output stream so we can get a handle on the log content
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(HandlerSpec)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get(requestPath)

    expect:
    loggerOutput.toString().contains(expectedLogEntry)

    where:
    requestPath   | expectedLogEntry
    "user"        | "GET /user 200 id="
    "user/1"      | "GET /user/1 200 id="

    /*
    Hint:
    You'll need a handler that gets called for _all_ requests regardless of their Http method or path
    */
  }

  @Unroll
  def "12 - can send a POST request with a soap action of #soapAction"() {
    given:
    // Using TestHttpClient.requestSpec we can configure details of this request like adding headers and setting the body
    requestSpec { RequestSpec req ->
      req.headers.set("SOAPAction", soapAction)
    }

    expect:
    postText("api/ws") == "api/ws - $soapAction"

    where:
    soapAction << ["getTweets", "getFriends"]

    /*
    Hint:
    You're all out of hints :)
    */
  }
}
