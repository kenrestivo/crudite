(ns crudite.widgets)

(defn link-button
  [ link text]
  [:div {:class "linkbutton" }
   [:a {:href link}
    text]])
  