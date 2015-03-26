import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.handling.RequestId
import ratpack.path.PathBinding

import static ratpack.groovy.Groovy.ratpack

final Logger log = LoggerFactory.getLogger(Ratpack);

ratpack {
  handlers {
    handler(RequestId.bindAndLog()) // log all requests

    prefix("user") {
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

    prefix("api/ws") { // if the prefix is removed, the tests still pass.  Must be careful that handlers further up the chain don't also match
      header("SOAPAction", "getTweets") {
        response.send "${get(PathBinding).boundTo} - getTweets"
      }

      header("SOAPAction", "getFriends") {
        response.send "${get(PathBinding).boundTo} - getFriends"
      }
    }

    prefix("assets") {
      assets("public")
    }

    assets("pages", "index.html")

    handler {
      response.send "Hello Greach!"
    }

  }
}
