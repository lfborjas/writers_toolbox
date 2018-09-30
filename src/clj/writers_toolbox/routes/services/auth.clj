(ns writers-toolbox.routes.services.auth
  (:require [writers-toolbox.db.core :as db]
            [ring.util.http-response :as response]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log]))

(defn register! [{:keys [session]} user]
  (try
    (db/create-user!
     (-> user
         (dissoc :pass-confirm)
         (update :pass hashers/encrypt)))
    (-> {:result :ok}
        (response/ok)
        (assoc :session (assoc session :identity (:id user))))
    (catch Exception e
      (log/error e)
      (response/internal-server-error
       {:result :error
        :message "Error creating user"}))))

