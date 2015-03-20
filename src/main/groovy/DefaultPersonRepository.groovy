class DefaultPersonRepository implements PersonRepository {
  @Override
  Person getPerson(long id) {
    if (id == 1) {
      return new Person(id: id, status: "new", name: "Frank Sinatra")
    } else {
      return null
    }
  }
}
