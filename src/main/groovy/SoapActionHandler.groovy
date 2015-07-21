import ratpack.groovy.Groovy
import ratpack.groovy.handling.GroovyContext
import ratpack.handling.internal.WhenHandler

class SoapActionHandler extends WhenHandler {

    SoapActionHandler(String soapAction,
                      @DelegatesTo(value = GroovyContext.class, strategy = Closure.DELEGATE_FIRST) Closure<?> handler) {

        super({ context -> context.request.headers.SOAPAction == soapAction }, Groovy.groovyHandler(handler))
    }

}
