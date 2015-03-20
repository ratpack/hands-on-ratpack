import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.test.ApplicationUnderTest
import ratpack.test.http.TestHttpClient
import spock.lang.Shared
import spock.lang.Specification

class PersonSpec extends Specification {
  @Shared ApplicationUnderTest appUnderTest = new GroovyRatpackMainApplicationUnderTest()
  @Delegate TestHttpClient testClient = appUnderTest.httpClient

  def "01 - can get a person's name"() {
    expect:
    getText("person/1/name") == "Frank Sinatra"
  }

  def "02 - can get a person's status"() {
    expect:
    getText("person/1/status") == "new"
  }

  def "03 - can return 404 for Person not found"() {
    expect:
    get("person/2/status").status.code == 404
  }
}
