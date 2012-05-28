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

   ;; these are the routes to make. override this to take some out.
   :to-build #{:list-all :get-view-one :get-add :post-add :get-edit :post-update}
   })



(defn get-merged
  [fst k]
  (k (merge defaults fst)))
 


(defn sanity-check
  [fst]
  (when-not (map? (:form-opts fst)) 
    (throw (Exception. ":form-opts must be a map")))
  (when-not (.endsWith (:view-url fst) "/") 
    (throw (Exception. ":view-url must have trailing slash")))
  (when (.startsWith (:list-url fst) (:view-url fst))
    (throw (Exception. ":list-url must not be same as :view-url minus slash")))
  fst)
  
  

;; TODO: call sanity czech
(defn merge-fsettings
  [fst]
  (sanity-check (merge defaults fst)))
