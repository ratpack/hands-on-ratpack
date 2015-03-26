import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registries.just

ratpack {
  bindings {
    /*
    * TODO bind DefaultBookService as the implementation of BookService
    * TODO bind DefaultBookRepository as the implementation of BookRepository
    *
    * Take a look at:
    * `ratpack.groovy.guice.GroovyBindingsSpec#bind(publicType, implType)`
    */
  }

  handlers {
    prefix("book/:isbn") {
      handler { BookService bookService ->
        long isbn = allPathTokens.asLong("isbn")
        Book b = bookService.getBook(isbn)

        next(just(b))
      }

      get("title") { Book b ->
        response.send b.title
      }

      get("author") { Book b ->
        response.send b.author
      }
    }
  }
}
