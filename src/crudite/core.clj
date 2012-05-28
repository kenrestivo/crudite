(ns crudite.core
  (:require 
   [crudite.actions :as actions])
  (:use [crudite.settings  :only [get-merged merge-fsettings]]
        [noir.core :only [defpage]]))





(defmacro make-forms
  [fst]
  (let [post-update (eval `[:post (str (get-merged ~fst :view-url)
                                       ":_id/update")])
        post-add (eval `[:post  (str (get-merged  ~fst :view-url) "add")])
        get-add (eval `(str (get-merged ~fst :view-url) "add"))
        get-edit (eval `(str (get-merged ~fst :view-url) ":id/edit"))
        get-view-one (eval `(str (get-merged ~fst :view-url) ":id"))
        list-all (eval `(get-merged ~fst :list-url))]
    `(do
       
       ;; the list  of all the items
       (defpage ~list-all  {:as fields5#}
         (actions/list-all-action (merge-fsettings ~fst) fields5#))
       
       
       ;; the view one page
       (defpage ~get-view-one {:as fields4#}
         (actions/view-one-action (merge-fsettings ~fst) fields4#))
       
       
       ;; the edit page
       (defpage ~get-edit  {:as fields3#}
         (actions/edit-get-action (merge-fsettings ~fst) fields3#))

       
       ;; the add page
       (defpage ~get-add   {:as fields2#}
         (actions/add-get-action (merge-fsettings ~fst) fields2#))

       
       ;; the UPDATE action return value  
       (defpage ~post-update   {:as fields1#}
         (actions/update-post-action (merge-fsettings ~fst) fields1#))
       
       ;; the ADD action return value
       (defpage ~post-add   {:as fields#}
         (actions/add-post-action (merge-fsettings ~fst) fields#)))))







