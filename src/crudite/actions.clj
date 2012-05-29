(ns crudite.actions
  (:use
   [crudite.components :only [make-form save-and-redirect]]
   [noir.core :only [render]]))


(defn add-post-action
  [fst fields]
  (if ((:form-valid? fst) fields false)
    (save-and-redirect fst fields false)
    (render (str (:view-url fst) "add")  fields)))


(defn update-post-action
  [fst fields]
  (if ((:form-valid? fst) fields true)
    (save-and-redirect fst fields true)
    ((:wrap-layout fst)
     {:title  (str "Edit " (:title-singular fst))
      :content (make-form fst fields true)})))


(defn add-get-action
  [fst fields]
  ((:wrap-layout fst)
   {:title  (str "Add " (:title fst))
    :content (make-form fst fields false)}))

(defn edit-get-action
  [fst {:keys [id]}]
  (if-let [fields ((:get-one fst) id)]
    ((:wrap-layout fst)
     {:title  (str "Edit " (:title-singular fst))
      :content (make-form fst fields true)})
    {:status 404 :body nil}))


(defn view-one-action
  [fst {:keys [id]}]
  (if-let [fields ((:get-one fst) id)]
    ((:wrap-layout fst)
     {:title  (str "View " (:title-singular fst))
      :content [:div
                ((:format-view fst) fields)
                (when ((:edit-auth? fst) fields)
                  ((:edit-button fst)
                   (str (:view-url fst)
                        id "/edit") "Edit"))]})
    {:status 404 :body nil}))


(defn list-all-action
  [fst fields]
  ((:wrap-layout fst)
   {:title  (:title fst)
    :content [:div
              ((:list-page fst) fields)
              (when ((:add-auth? fst) fields)
                ((:add-button fst)
                 (str (:view-url fst) "add")
                 (str "Add " (:title-singular fst))))]}))

