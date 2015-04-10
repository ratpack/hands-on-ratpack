# Lab 01 - Handler lab

Handlers are the fundamental component of any Ratpack application.  All request processing is done by composing a chain 
of handlers.  Unlike the routing table model you’re probably more used to.

When a request comes in, a "Context" is created which represents the current state.
The context gives access to the request, the response and some other things we’ll see in future labs.
The context is passed to the handler chain and each handler in the chain is asked to respond until one does.  A handler 
can respond, do something and pass control to the next handler or insert new handlers.

In this lab simply make all the feature methods in `HandlersSpec` pass by adding the right handlers to `Ratpack.groovy`.
Try to make each feature method pass in order and refactor as you go.

## This lab covers

* Simple routing
* Routing by HTTP method
* Routing by HTTP header
* Grouping handlers with the same prefix
* Routing by regular expression
* Using path tokens
* Static assets

## Sign Posts
`ratpack.handling.Chain`

`ratpack.handling.Context`

`ratpack.groovy.handling.GroovyChain`

`ratpack.groovy.handling.GroovyContext`

`ratpack.path.PathBinding`


http://www.ratpack.io/manual/current/handlers.html#handlers

https://github.com/ratpack/ratpack/blob/master/ratpack-core/src/test/groovy/ratpack/path/PathRoutingSpec.groovy

https://github.com/ratpack/ratpack/blob/master/ratpack-core/src/test/groovy/ratpack/path/PathAndMethodRoutingSpec.groovy

https://github.com/ratpack/ratpack/blob/master/ratpack-core/src/test/groovy/ratpack/path/internal/TokenPathBinderSpec.groovy