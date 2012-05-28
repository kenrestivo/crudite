(ns crudite.settings
  (:use
   [hiccup.core :only [html]]
   [hiccup.form :only [form-to label submit-button
                       text-field file-upload drop-down]]
   [noir.core :only [defpage defpartial render]])
  (:require
   [crudite.actions :as actions]
   [crudite.components :as components]
   [crudite.widgets :as widgets]
   [noir.response :as response]
   [noir.session :as session]
   [noir.validation :as validation]))





;;; this will go into the views themselves
;;; core here will merge the map
(def defaults
  {:list-url "/things"
   :view-url "/thing/"
   :title "Things"
   :title-singular "Thing"
   
   ;; get-one takes id, returns field map
   :get-one (fn [id] {:_id id})
   
   ;; make-fields needs to return a defpartial with the fields of the form
   :make-fields (fn [fields update?] (str fields  update? "Make the fields here"))
   
   ;; list-page takes a field map (pagination, not save fields),
   ;; and returns the formatted item listing
   :list-page (fn [fields] [:ul [:li "items" ]])
   
   ;; format-view is the view (for one record), takes a field map, returns hiccup
   :format-view (fn [fields] [:p (str fields)])
   
   ;; form-valid? is the validation rules, takes field map, returns true/false
   :form-valid? (constantly true)
   
   ;;; buttons to use
   :edit-button   widgets/link-button
   :add-button widgets/link-button
   :save-button submit-button
   
   ;; wrap-layout: takes {:title :content} and returns formatted body in a  ring map
   :wrap-layout (fn [{:keys [title content]}]
                     (html [:head [:title title]]
                           [:body [:div content]]))
                                   

   ;; form-opts: additional form opts, like multipart-binary, etc
   :form-opts {}

   ;; save-func: takes a field map and saves it. 
   :save-func (fn [fields] true)
   })



(defn get-merged
  [fst k]
  (k (merge defaults fst)))
 


;;; TODO: maybe do some sanity checking here?
;;; view-url must have trailing /
;;; list-url must not be same as view-url minus slash!
;;; form-opts must be a map
(defn sanity-check
  [fst]
  true)
  

;; TODO: call sanity czech
(defn merge-fsettings
  [fst]
  (merge defaults fst))
