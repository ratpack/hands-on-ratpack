import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registries.just

ratpack {
  handlers {
    register just(DefaultPersonService.instance)

    prefix("person/:id") {
      handler { PersonService personService ->
        long id = allPathTokens.asLong("id")
        Person p = personService.getPerson(id)

        next(just(p))
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
