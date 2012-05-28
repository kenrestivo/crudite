(ns crudite.components
  (:use [hiccup.form :only [form-to]])
  (:require [noir.response :as response]
            [noir.session :as session]))


(defn save-and-redirect
  "Does the mechanics of saving/redirecting. Common to both add and update"
  [fst fields update?]
  ((:save-func fst) fields)
  (session/flash-put! :status
                      (str (:title fst) " " (:name fields) " "
                           (if update? "Saved!"  "Added")))
  (response/redirect (:list-url fst)))


(defn make-form
  [fst fields update?]
  (let [actionname "Save"
        title (str (if update? "Edit" "Add") " "
                   (:title-singular fst))]
    (form-to
     (:form-opts fst)
     [:post (str (:view-url fst)
                 (if update? (str (:_id fields) "/update") "add"))]
     [:div {:id "form" :class "form"}
      [:h2 title]
      ((:make-fields fst) fields update?)
      ((:save-button fst) actionname)])))


