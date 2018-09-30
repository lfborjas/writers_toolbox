(ns writers-toolbox.components.registration
  (:require [reagent.core :refer [atom]]
            [ajax.core :as ajax]
            [reagent.session :as session]
            [writers-toolbox.validation :refer [registration-errors]]
            [writers-toolbox.components.common :as c]))

(defn registration-form []
  (let [fields (atom {})
        error (atom nil)]
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
         :pass-confirm "re-enter the password" fields]
        (when-let [error (:server-error @error)]
          [:div.alert.alert-danger error])]
       [:div
        [:button.btn.btn-primary
         {:on-click #(register! fields error)}
         "Register"]
        [:button.btn.btn-danger "Cancel"]]])))

(defn register! [fields errors]
  (reset! errors (registration-errors @fields))
  (when-not @errors
    (ajax/POST "/register"
               {:params @fields
                :handler
                #(do
                   (session/put! :identity (:id @fields))
                   (reset! fields {}))
                :error-handler
                #(reset! errors
                         {:server-error (get-in % [:response :message])})})))
