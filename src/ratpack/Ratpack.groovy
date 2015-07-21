import ratpack.handling.RequestId
import ratpack.path.PathBinding

import static ratpack.groovy.Groovy.ratpack

ratpack {
    handlers {
        all(RequestId.bindAndLog()) // log all requests

        prefix("user", new UserEndpoint())

        prefix("api/ws") {
            // if the prefix is removed, the tests still pass.  Must be careful that handlers further up the chain don't also match
            all(new SoapActionHandler("getFriends", {
                response.send "${get(PathBinding).boundTo} - getFriends"
            }))

            // Here we are using a Groovy extension module to add `soapAction` to `GroovyChain`
            soapAction("getTweets") {
                response.send "${get(PathBinding).boundTo} - getTweets"
            }
        }

        prefix("assets") {
            files { dir "public" }
        }

        files { dir "pages" indexFiles "index.html" }

        all {
            response.send "Hello Greach!"
        }

    }
}
