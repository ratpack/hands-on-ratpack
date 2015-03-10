import ratpack.groovy.Groovy
import ratpack.groovy.handling.GroovyContext
import ratpack.http.internal.HeaderHandler

class SoapActionHandler extends HeaderHandler {

  SoapActionHandler(String soapAction, @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {
    super("SOAPAction", soapAction, Groovy.groovyHandler(handler))
  }

}
