import groovy.io.GroovyPrintStream
import org.slf4j.LoggerFactory
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.handling.RequestId
import ratpack.http.client.RequestSpec
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class HandlerSpec extends Specification {
  @Shared ApplicationUnderTest aut = new GroovyRatpackMainApplicationUnderTest()
  @Delegate TestHttpClient client = aut.httpClient

  def "01 - can send to root path"() {
    expect:
    getText() == "Hello Greach!"
  }

  def "02 - can send GET to path 'user'"() {
    expect:
    getText("user") == "user"
  }

  def "03 - can send PUT to path 'foo'"() {
    expect:
    put("user").statusCode == 405
  }

  @Unroll
  def "04 - can send GET to path 'user/#username'"() {
    expect:
    getText("user/$username") == "user/$username"

    where:
    username << ["dave", "fred"]
  }

  @Unroll
  def "05 - can send GET to path 'user/#username/tweets'"() {
    expect:
    getText("user/$username/tweets") == "user/$username/tweets"

    where:
    username << ["dave", "fred"]
  }

  @Unroll
  def "06 - can send GET to path 'user/#username/friends'"() {
    expect:
    getText("user/$username/friends") == "user/$username/friends"

    where:
    username << ["dave", "fred"]
  }

  @Unroll
  def "07 - can send POST with soap action of #soapAction"() {
    given:
    requestSpec { RequestSpec req ->
      req.headers.set("SOAPAction", soapAction)
    }

    expect:
    postText("api/ws") == soapAction

    where:
    soapAction << ["getTweets", "getFriends"]
  }

  def "08 - can log all requests"() {
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(RequestId)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get("user")

    expect:
    loggerOutput.toString().contains("GET /user 200 id=")
  }

  @Unroll
  def "09 - can log warning for user starting with 'f': #username"() {
    def loggerOutput = new ByteArrayOutputStream()
    def logger = LoggerFactory.getLogger(RequestId)
    logger.TARGET_STREAM = new GroovyPrintStream(loggerOutput)

    given:
    get("user/$username/tweets")

    expect:
    loggerOutput.toString().contains("Warning, request for $username")

    where:
    username << ["florence", "fred"]
  }

  def "10 - can send POST to path 'user'"() {
    expect:
    postText("user") == "user"
  }
}
