(ns writers-toolbox.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [writers-toolbox.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[writers-toolbox started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[writers-toolbox has shut down successfully]=-"))
   :middleware wrap-dev})
