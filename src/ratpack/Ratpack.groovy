import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registries.just

ratpack {
  bindings {
    bind PersonRepository, DefaultPersonRepository
    bind PersonService, DefaultPersonService
  }

  handlers {
    prefix("person/:id") {
      handler { PersonService personService ->
        long id = allPathTokens.asLong("id")
        Person p = personService.getPerson(id)

        if (p == null) {
          response.status(404).send()
        } else {
          next(just(p))
        }
      }

      get("name") {
        response.send context.get(Person).name
      }

      get("status") { Person p ->
        response.send p.status
      }
    }
  }
}
