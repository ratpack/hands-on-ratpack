import static ratpack.groovy.Groovy.ratpack
import static ratpack.registry.Registries.just

ratpack {
  handlers {
    register just(DefaultBookService.instance)

    prefix("book/:isbn") {
      handler { BookService bookService ->
        long isbn = allPathTokens.asLong("isbn")
        Book b = bookService.getBook(isbn)

        next(just(b))
      }

      get("title") {
        response.send context.get(Book).title
      }

      get("author") { Book b ->
        response.send b.author
      }
    }
  }
}
