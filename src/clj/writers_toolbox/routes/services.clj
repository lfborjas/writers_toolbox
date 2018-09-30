(ns writers-toolbox.routes.services
  (:require [writers-toolbox.routes.services.auth :as auth]
            [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]))

(defn access-error [_ _]
  (unauthorized {:error "unauthorized"}))

(defn wrap-restricted [handler rule]
  (restrict handler {:handler  rule
                     :on-error access-error}))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-restricted rule]))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))

(s/defschema UserRegistration
  {:id String
   :pass String
   :pass-confirm String
   :first_name String
   :last_name String
   :email String})

(s/defschema Result
  {:result s/Keyword
   (s/optional-key :message) String})

(defapi service-routes
  {:swagger {:ui "/swagger-ui"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Writer's Toolbox API"
                           :description "Public Services"}}}}

  (POST "/register" req
        :return Result
        :body [user UserRegistration]
        :summary "Register a new user"
        (auth/register! req user))
  ; this was already here, as part of the generation
  (GET "/authenticated" []
       :auth-rules authenticated?
       :current-user user
       (ok {:user user})))
