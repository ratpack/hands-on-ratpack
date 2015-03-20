import javax.inject.Inject

class DefaultPersonService implements PersonService {

  private final PersonRepository repo

  @Inject
  public DefaultPersonService(PersonRepository repo) {
    this.repo = repo
  }

  @Override
  Person getPerson(long id) {
    return repo.getPerson(id)
  }
}
