(ns writers-toolbox.components.authentication
  (:require [reagent.core :refer [atom]]
            [reagent.session :as session]
            [goog.crypt.base64 :as b64]
            [clojure.string :as string]
            [ajax.core :as ajax]
            [writers-toolbox.components.common :as c]))

(defn encode-auth [user pass]
  (->> (str user ":" pass)
       (b64/encodeString)
       (str "Basic ")))


(defn login! [fields error]
  (let [{:keys [id pass]} @fields]
    (reset! error nil)
    (ajax/POST "/login"
               {:headers {"Authorization" (encode-auth
                                           (string/trim id)
                                           pass)}
                :handler #(do
                            (session/remove! :modal)
                            (session/put! :identity id)
                            (reset! fields nil))
                :error-handler #(reset! error
                                        (get-in % [:response :message]))})))

(defn login-form []
  (let [fields (atom {})
        error (atom nil)]
    (fn []
      [c/modal
       [:div "Login"]
       [:div
        [:div.well.well-sm
         [:strong "* required"]]
        [c/text-input "Username" :id "username" fields]
        [c/password-input "Password" :pass "password" fields]
        (when-let [error @error]
          [:div.alert.alert-danger error])]
       [:div
        [:button.btn.btn-primary
         {:on-click #(login! fields error)}
         "Login"]
        [:button.btn.btn-danger
         {:on-click #(session/remove! :modal)}
         "Cancel"]]])))

(defn login-button []
  [:a.btn
   {:on-click #(session/put! :modal login-form)}
   "Sign In"])
