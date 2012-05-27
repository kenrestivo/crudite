(ns crudite.core
  (:use
   [hiccup.core :only [html]]
   [hiccup.form :only [form-to label submit-button
                       text-field file-upload drop-down]]
   [noir.response :only [redirect]]
   [noir.core :only [defpage defpartial render]])
  (:require
   [noir.session :as session]
   [noir.validation :as validation]))



(defmacro defpage-hack
  "Wraps noir.core/defpage, after eval'ing the url argument,
    so that you can pass the url as (str something something) or (:keyname m)
    instead of a static string or vector."
  [url des & all]
  (let [cleaned (eval url)]
    `(defpage ~cleaned ~des ~@all)))



;;; make-fields needs to return a defpartial with the fields of the form
;;; form-valid? is the validation rules, takes field map, returns true/false
;;; format-view is the view (for one record), takes a field map, returns hiccup



;;; this will go into the views themselves
;;; core here will merge the map
(def fsettings {:list-url "/things"
                :view-url "/thing/"
                :title "Things"
                :title-singular "Thing"
                :get-one things/get-thing
                :make-fields make-fields
                :list-page #'list-page
                :format-view #'format-view
                :form-valid? form-valid?
                :edit-button widgets/edit-button
                :add-button widgets/add-button
                :wrap-layout widgets/wrap-layout
                :save-button submit-button
                :form-opts {:enctype "multipart/form-data"}
                :save-func things/save-thing!
                })




;;;;;;;;; generic abstractions below
;;; TODO: figure out  how to make this a macro that will actually work!

(defn make-form
  [fields update?]
  (let [actionname "Save"
        title (str (if update? "Edit" "Add") " "
                   (:title-singular fsettings))]
    (form-to
     (:form-opts fsettings)
     [:post (str (:view-url fsettings)
                 (if update? (str (:_id fields) "/update") "add"))]
     [:div {:id "form" :class "form"}
      [:h2 title]
      ((:make-fields fsettings) fields update?)
      ((:save-button fsettings) actionname)])))



(defn save-and-redirect
  "Does the mechanics of saving/redirecting. Common to both add and update"
  [fields update?]
  ((:save-func fsettings) fields)
  (session/flash-put! :status
                      (str (:title fsettings) " " (:name fields) " "
                           (if update? "Saved!"  "Added")))
  (response/redirect (:list-url fsettings)))



;;; the list  of all the items
(utils/defpage-hack (:list-url fsettings)  []
  ((:wrap-layout fsettings)
   {:title  (:title fsettings)
    :content [:div
              ((:list-page fsettings))
              ((:add-button fsettings)
               (str (:view-url fsettings) "add")
               (str "Add " (:title-singular fsettings)))]}))
                    


;; the view page
(utils/defpage-hack (str (:view-url fsettings) ":id") {:keys [id]}
  (if-let [fields ((:get-one fsettings) id)]
    ((:wrap-layout fsettings)
     {:title  (str "View " (:title-singular fsettings))
      :content [:div
                ((:format-view fsettings) fields)
                ((:edit-button fsettings)
                 (str (:view-url fsettings) id "/edit") "Edit")]})
    {:status 404 :body nil}))


;; the edit page
(utils/defpage-hack (str (:view-url fsettings) ":id/edit")  {:keys [id]}
  (if-let [fields ((:get-one fsettings) id)]
    ((:wrap-layout fsettings)
     {:title  (str "Edit " (:title-singular fsettings))
      :content (make-form fields true)})
  {:status 404 :body nil}))




;;; the add page
(utils/defpage-hack (str (:view-url fsettings) "add")  {:as fields}
  ((:wrap-layout fsettings)
   {:title  (str "Add " (:title fsettings))
    :content (make-form fields false)}))


;;; the UPDATE action return value
(utils/defpage-hack [:post (str (:view-url fsettings) ":_id/update")]
  {:as fields}
  (if ((:form-valid? fsettings) fields true)
    (save-and-redirect fields true)
    ((:wrap-layout fsettings)
     {:title  (str "Edit " (:title-singular fsettings))
      :content (make-form fields true)})))
  

;; the ADD action return value
(utils/defpage-hack [:post (str (:view-url fsettings) "add")]  {:as fields}
  (if ((:form-valid? fsettings) fields false)
    (save-and-redirect fields false)
    (render (str (:view-url fsettings) "add")  fields)))








