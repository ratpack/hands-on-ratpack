import groovy.util.logging.Slf4j
import ratpack.func.Action
import ratpack.groovy.Groovy
import ratpack.handling.Chain
import ratpack.path.PathBinding

@Slf4j
class UserEndpoint implements Action<Chain> {

  @Override
  void execute(Chain chain) throws Exception {
    Groovy.chain(chain) {
      handler("::f.*") { // starts with 'f' then 0 or more characters
        def binding = get(PathBinding)
        def username = binding.boundTo.indexOf('/') < 0 ?
            binding.boundTo :
            binding.boundTo.substring(0, binding.boundTo.indexOf('/'))

        log.info "Warning, request for $username"
        next()
      }

      prefix(":username") {
        get {
          response.send "user/${allPathTokens['username']}" // had to use `allPathTokens`
        }

        get("tweets") {
          response.send "user/${allPathTokens['username']}/tweets"
        }

        get("friends") {
          response.send "user/${allPathTokens['username']}/friends"
        }
      }

      handler {
        byMethod {
          get {
            response.send "user"
          }
          post {
            response.send "user"
          }
        }
      }
    }
  }

}
