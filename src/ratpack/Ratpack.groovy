import ratpack.registry.Registries
import ratpack.registry.RegistrySpec

import static ratpack.groovy.Groovy.ratpack

ratpack {
  handlers {
    // For this use case there are better options than this.  We will see in future labs
    register { RegistrySpec spec ->
      spec.add(DefaultPersonService.instance)
    }

    prefix("person/:id") {
      handler { PersonService personService ->
        long id = allPathTokens.asLong("id")
        Person p = personService.getPerson(id)

        next(Registries.just(p))
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
