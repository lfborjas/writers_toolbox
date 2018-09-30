(ns user
  (:require [writers-toolbox.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [writers-toolbox.figwheel :refer [start-fw stop-fw cljs]]
            [writers-toolbox.core :refer [start-app]]
            [writers-toolbox.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'writers-toolbox.core/repl-server))

(defn stop []
  (mount/stop-except #'writers-toolbox.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'writers-toolbox.db.core/*db*)
  (mount/start #'writers-toolbox.db.core/*db*)
  (binding [*ns* 'writers-toolbox.db.core]
    (conman/bind-connection writers-toolbox.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


