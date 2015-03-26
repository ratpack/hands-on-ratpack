import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registries.just

ratpack {
  bindings {
    bind BookRepository, DefaultBookRepository
    bind BookService, DefaultBookService
  }

  handlers {
    prefix("book/:isbn") {
      handler { BookService bookService ->
        String isbn = allPathTokens["isbn"]
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
