import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    bind PersonRepository, DefaultPersonRepository
    bind PersonService, DefaultPersonService
  }

  handlers {

  }
}
