(ns writers-toolbox.components.registration
  (:require [reagent.core :refer [atom]]
            [writers-toolbox.components.common :as c]))

(defn registration-form []
  (let [fields (atom {})]
    (fn []
      [c/modal
       [:div "Writer's Toolbox Registration"]
       [:div
        [:div.well.well-sm
         [:strong "* required field"]]
        [c/text-input "Username" :id "enter a user name" fields]
        [c/text-input "First Name" :first_name "first name" fields]
        [c/text-input "Last Name" :last_name "last name" fields]
        [c/text-input "Email" :email "email" fields]
        [c/password-input "Password" :pass "enter a password" fields]
        [c/password-input "Confirm password"
         :pass-confirm "re-enter the password" fields]]
       [:div
        [:button.btn.btn-primary "Register"]
        [:button.btn.btn-danger "Cancel"]]])))
