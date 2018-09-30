(ns writers-toolbox.handler
  (:require [writers-toolbox.middleware :as middleware]
            [writers-toolbox.layout :refer [error-page]]
            [writers-toolbox.routes.home :refer [home-routes]]
            [writers-toolbox.routes.services :refer [service-routes]]
            [compojure.core :refer [routes wrap-routes]]
            [ring.util.http-response :as response]
            [compojure.route :as route]
            [writers-toolbox.env :refer [defaults]]
            [mount.core :as mount]))

(mount/defstate init-app
  :start ((or (:init defaults) identity))
  :stop  ((or (:stop defaults) identity)))

(mount/defstate app
  :start
  (middleware/wrap-base
    (routes
      (-> #'home-routes
          (wrap-routes middleware/wrap-csrf)
          (wrap-routes middleware/wrap-formats))
          #'service-routes
          (route/not-found
             (:body
               (error-page {:status 404
                            :title "page not found"}))))))

