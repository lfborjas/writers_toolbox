(ns writers-toolbox.validation
  (:require [struct.core :as st]))

;; the Web Development with Clojure book uses Bouncer as the validation library [0], but Luminus seems to have gone with Struct [1] these days, as that's the one bundled with the template now.
;; [0] https://github.com/leonardoborges/bouncer
;; [1] https://funcool.github.io/struct/latest/#quick-start
;; this file is my attempt at translating the book's examples to Struct!

(def registration-schema
  {:id st/required
   :pass-confirm st/required
   :pass [st/required
          [st/min-count 7
           :message "Password must contain at least 8 characters"]
          [st/identical-to :pass-confirm
           :message "Password and confirmation must match"]]})

(defn registration-errors [{:keys [pass-confirm] :as params}]
  (first (st/validate params registration-schema)))
