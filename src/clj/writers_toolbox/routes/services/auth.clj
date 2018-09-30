(ns writers-toolbox.routes.services.auth
  (:require [writers-toolbox.db.core :as db]
            [writers-toolbox.validation :refer [registration-errors all-messages]]
            [ring.util.http-response :as response]
            [buddy.hashers :as hashers]
            [clojure.tools.logging :as log]))

(defn handle-registration-error [e]
  (if (-> e
          ;; the book advocates for checking this is a SQLException
          ;; but .getCause seems to do this better (calling .getNextExc
          ;; without .getCause raises a further exception since
          ;; java.lang.Exception doesn't have that method
          ;; see: https://stackoverflow.com/questions/7134454/clojure-how-do-i-access-sqlexception-from-a-clojure-leiningen-project#comment8553973_7134621
          (.getCause)
          (.getNextException)
          (.getMessage)
          (.startsWith "ERROR: duplicate key value"))
    (response/precondition-failed
     {:result :error
      :message "Username already exists"})
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

