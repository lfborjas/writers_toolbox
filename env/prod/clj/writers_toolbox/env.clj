(ns writers-toolbox.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[writers-toolbox started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[writers-toolbox has shut down successfully]=-"))
   :middleware identity})
