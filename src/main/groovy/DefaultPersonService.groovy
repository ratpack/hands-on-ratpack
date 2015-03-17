@Singleton
class DefaultPersonService implements PersonService {
  @Override
  Person getPerson(long id) {
    return new Person(id: id, status: "new", name: "Frank Sinatra")
  }
}
