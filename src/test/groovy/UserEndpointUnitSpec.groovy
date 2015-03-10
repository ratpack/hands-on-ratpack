import ratpack.groovy.test.handling.GroovyRequestFixture
import spock.lang.Specification
import spock.lang.Unroll

class UserEndpointUnitSpec extends Specification {

  @Unroll
  def "can send #requestHttpMethod request to #requestUri"() {
    expect:
    def result = GroovyRequestFixture.handle(new UserEndpoint()) {
      method requestHttpMethod
      uri requestUri
    }

    result.bodyText == expectedResponse

    where:
    requestUri      | requestHttpMethod   | expectedResponse
    ""              | "get"               | "user"
    ""              | "post"              | "user"
    "/dave"         | "get"               | "user/dave"
    "/fred"         | "get"               | "user/fred"
    "/dave/tweets"  | "get"               | "user/dave/tweets"
    "/fred/tweets"  | "get"               | "user/fred/tweets"
    "/dave/friends" | "get"               | "user/dave/friends"
    "fred/friends"  | "get"               | "user/fred/friends"
  }

}
