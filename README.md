# writers-toolbox

Miscellaneous tools to help with the administrivia of creative writing projects (e.g. keep track of word goals like in [NaNoWriMo](https://nanowrimo.org/participants/lfborjas/novels/errante-1188140/stats))


## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run 
    
To get clojurescript compilation going, run 

    lein figwheel
    
Since you often need to do both, I recommend split panes in tmux to keep an eye on both.


## Development

`generated using Luminus version "3.10.1"`

Based on the relatively usable but already outdated tutorial in Chapter 8 of [Web Development with Clojure, 2nd Edition](https://pragprog.com/book/dswdcloj2/web-development-with-clojure-second-edition), written by [one](http://yogthos.net/posts/2016-01-01-ClojureWebDev2.html) of the [core maintainers of Luminus](https://github.com/luminus-framework/luminus-template/commits?author=yogthos). Read the commit log for instances where I had to reverse-engineer the intended functionality--can't say I didn't learn a thing or two about how fast the Luminus maintainers learn and their zeal to keep things clean! (Removing dependencies on CIDER, `reagent-utils` and the global `*identity*` come to mind: annoying to have to re-create, good to know they didn't hesitate to move on from those antipatterns).

### Swagger UI

One very neat thing about this Luminus template I used here is that it ships with a very full featured Swagger UI, which actually is able to interact with the code:

![image](https://user-images.githubusercontent.com/82133/46264211-f57e8880-c4e7-11e8-936b-77e864e2dee4.png)


Protip: to be able to authenticate, you'll need to send a Base64-encoded `Authorization` header. Here's a cheap function that can generate it:

```clojure
writers-toolbox.routes.services.auth=> (defn creds [u p] 
  (str "Basic " 
      (.encodeToString 
          (java.util.Base64/getEncoder) 
          (.getBytes (str u ":" p)))))
#'writers-toolbox.routes.services.auth/creds
writers-toolbox.routes.services.auth=> (creds "lady" "12341234")
"Basic bGFkeToxMjM0MTIzNA=="
```

![image](https://user-images.githubusercontent.com/82133/46264217-04fdd180-c4e8-11e8-84df-ea609135c782.png)



### Emacs



I assume emacs use up in here, buddy. You probably are used to use Cider.
I have bad news for you: Luminus, Emacs 24 and cider don't play well. There's a new version of Cider that [doesn't need black magic](https://github.com/clojure-emacs/cider/blob/master/doc/installation.md#ciders-nrepl-middleware) and fasting to work with luminus (see: https://github.com/luminus-framework/luminus-template/pull/376/files). However, to install that version, it looks like one needs emacs 25. I have 24. 

However, you can use [monroe](https://github.com/sanel/monroe), as recommended by the [nrepl folks](https://github.com/clojure/tools.nrepl).

Since the Luminus template [already sets things up](http://www.luminusweb.net/docs/repl.html#connecting_to_the_nrepl) to boot up an nREPL when you execute `lein run` (based on the config in `dev-config.edn`), you should be able to connect to your REPL using `M-x monroe` and selecting `localhost:7000` as the server.
