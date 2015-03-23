import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.render.GroovyRendererSupport
import ratpack.jackson.Jackson

import static ratpack.groovy.Groovy.markupBuilder

class PersonRenderer extends GroovyRendererSupport<Person> {
  @Override
  void render(GroovyContext context, Person object) throws Exception {
    context.byContent {
      json {
        render Jackson.json(object)
      }
      xml {
        context.render markupBuilder("application/xml", "UTF-8") {
          person {
            status object.status
            name object.name
            id object.id
          }
        }
      }
    }
  }
}