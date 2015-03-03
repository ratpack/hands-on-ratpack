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
      handler("::f+.*") { // starts with 'f' then 1 or more characters
        def binding = get(PathBinding)
        def username = binding.boundTo.indexOf('/') < 0 ?
            binding.boundTo :
            binding.boundTo.substring(0, binding.boundTo.indexOf('/'))

        log.info "Warning, request for $username"
        next()
      }

      prefix(":username") {
        get {
          render "user/${allPathTokens.get('username')}" // had to use `allPathTokens`
        }

        get("tweets") {
          render "user/${allPathTokens.get('username')}/tweets"
        }

        get("friends") {
          render "user/${allPathTokens.get('username')}/friends"
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
        render "getTweets"
      }

      header("SOAPAction", "getFriends") {
        render "getFriends"
      }
    }

    handler {
      response.send "Hello Greach!"
    }

  }
}
