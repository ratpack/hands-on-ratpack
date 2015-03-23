import ratpack.groovy.template.MarkupTemplateModule
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind PersonRepository, DefaultPersonRepository
    bind PersonService, DefaultPersonService
    bind PersonRenderer
    add MarkupTemplateModule
    add JacksonModule
  }

  handlers {
    get {
      render "Hello Greach!"
    }

    get("welcome") {
      render groovyMarkupTemplate("index.gtpl", welcomeMessage: "Hello Greach!")
    }

    get("api/person/:id") { PersonService personService ->
      long id = allPathTokens.asLong("id")
      Person p = personService.getPerson(id)

      if (p == null) {
        response.status(404).send()
      } else {
        render p
      }
    }
  }
}
