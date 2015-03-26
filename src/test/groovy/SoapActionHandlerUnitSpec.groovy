import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification

class SoapActionHandlerUnitSpec extends Specification {

  def "can handle soap requests"() {
    given:
    def handlerUnderTest = new SoapActionHandler("foo", {
      response.send(request.headers.get("SOAPAction"))
    })

    when:
    // Using GroovyRequestFixture we can test the handler in isolation with different http methods, headers, uri's and more
    def result = GroovyRequestFixture.handle(handlerUnderTest) {
      header "SOAPAction", soapAction
    }

    then:
    result.sentResponse == responseSent
    result.bodyText == expectedResponse

    where:
    soapAction  | responseSent  |   expectedResponse
    "foo"       | true          |   "foo"
    "bar"       | false         |   null

    /*
    Hint:
    You will need to create `SoapActionHandler` in src/main/groovy
    Take a look at `ratpack.http.internal.HeaderHandler` and `ratpack.groovy.handling.internal.DefaultGroovyChain#header(headerName, headerValue, handler)`
    Try creating a `ratpack.groovy.handling.GroovyHandler` or extending `ratpack.http.internal.HeaderHandler`

    Don't forget to update Ratpack.groovy with your new handler and check `HandlerSpec` still passes
    */
  }

}
