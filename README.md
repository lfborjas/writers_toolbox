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

I assume emacs use up in here, buddy. You're probably using [Cider](). Since the Luminus template [already sets things up](http://www.luminusweb.net/docs/repl.html#connecting_to_the_nrepl) to boot up an nREPL when you execute `lein run` (based on the config in `dev-config.edn`), you should be able to [connect your cider repl as normal](https://github.com/clojure-emacs/cider#connect-to-a-running-nrepl-server), by executing `M-x cider-connect` from within emacs.
