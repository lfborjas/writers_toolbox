(ns writers-toolbox.routes.services.auth
  (:require [writers-toolbox.db.core :as db]
            [writers-toolbox.validation :refer [registration-errors all-messages]]
            [ring.util.http-response :as response]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log]))

(defn handle-registration-error [e]
  (if (and
       (instance? java.sql.BatchUpdateException  e)
       (-> e
           (.getNextException)
           (.getMessage)
           ;;TODO: this isn't working, it may be that the example in the ook expects different behavior
           (.startsWith "ERROR: duplicate key value")))
    (response/precondition-failed
     {:result :error
      :message "User with the selected ID already exists"})
    (do
      (log/error e)
      (response/internal-server-error
       {:result :error
        :message "Error while adding user!"}))))

(defn register! [{:keys [session]} user]
  (if-let [errors (registration-errors user)]
    (response/precondition-failed {:result :error
                                   :message (all-messages errors)})
    (try
      (db/create-user!
       (-> user
           (dissoc :pass-confirm)
           (update :pass hashers/encrypt)))
      (-> {:result :ok}
          (response/ok)
          (assoc :session (assoc session :identity (:id user))))
      (catch Exception e
        (handle-registration-error e)))))

