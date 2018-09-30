# writers-toolbox

generated using Luminus version "3.10.1"

FIXME

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run 
    
To get clojurescript compilation going, run 

    lein figwheel
    
Since you often need to do both, I recommend split panes in tmux to keep an eye on both.

## Development with Emacs

I assume emacs use up in here, buddy. You probably are used to use Cider.
I have bad news for you: Luminus, Emacs 24 and cider don't play well. There's a new version of Cider that [doesn't need black magic](https://github.com/clojure-emacs/cider/blob/master/doc/installation.md#ciders-nrepl-middleware) and fasting to work with luminus (see: https://github.com/luminus-framework/luminus-template/pull/376/files). However, to install that version, it looks like one needs emacs 25. I have 24. 

However, you can use [monroe](https://github.com/sanel/monroe), as recommended by the [nrepl folks](https://github.com/clojure/tools.nrepl).

Since the Luminus template [already sets things up](http://www.luminusweb.net/docs/repl.html#connecting_to_the_nrepl) to boot up an nREPL when you execute `lein run` (based on the config in `dev-config.edn`), you should be able to connect to your REPL using `M-x monroe` and selecting `localhost:7000` as the server.
