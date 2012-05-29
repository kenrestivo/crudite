(ns crudite.core
  (:require 
   [crudite.actions :as actions])
  (:use [crudite.settings  :only [get-merged merge-fsettings]]
        [noir.core :only [defpage]]))





(defmacro make-forms
  [fst-in]
  (let [fst `(merge-fsettings ~fst-in)
        post-update (eval `[:post (str (get-merged ~fst :view-url)
                                       ":_id/update")])
        post-add (eval `[:post  (str (get-merged  ~fst :view-url) "add")])
        get-add (eval `(str (get-merged ~fst :view-url) "add"))
        get-edit (eval `(str (get-merged ~fst :view-url) ":id/edit"))
        get-view-one (eval `(str (get-merged ~fst :view-url) ":id"))
        list-all (eval `(get-merged ~fst :list-url))]
    `(do
       
       ;; the list  of all the items
       (noir.core/defpage ~list-all  {:as fields5#}
         (actions/list-all-action ~fst fields5#))
       
       
       ;; the view one page
       (noir.core/defpage ~get-view-one {:as fields4#}
         (actions/view-one-action ~fst fields4#))
       
       
       ;; the edit page
       (noir.core/defpage ~get-edit  {:as fields3#}
         (actions/edit-get-action ~fst fields3#))

       
       ;; the add page
       (noir.core/defpage ~get-add   {:as fields2#}
         (actions/add-get-action ~fst fields2#))

       
       ;; the UPDATE action return value  
       (noir.core/defpage ~post-update   {:as fields1#}
         (actions/update-post-action ~fst fields1#))
       
       ;; the ADD action return value
       (noir.core/defpage ~post-add   {:as fields#}
         (actions/add-post-action ~fst fields#)))))







