(ns writers-toolbox.app
  (:require [writers-toolbox.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
