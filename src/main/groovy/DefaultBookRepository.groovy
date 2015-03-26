class DefaultBookRepository implements BookRepository {
  @Override
  Book getBook(String isbn) {
    return new Book(isbn: isbn, quantity: 10, price: 15.99, title: "Ratpack Web Framework",
        author: "Dan Woods", publisher: "O'Reilly")
  }
}
