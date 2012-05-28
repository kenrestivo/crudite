(ns crudite.unit
  (:use clojure.test
        crudite.core
        crudite.actions)
  (:require [crudite.settings :as settings]
            [noir.util.test]))




(doseq [a [add-post-action update-post-action]]
  (noir.util.test/with-noir
    (a settings/defaults {:id 1 :foo "bar" :baz "quuz"}))

  (noir.util.test/with-noir
    (a (settings/merge-fsettings {:form-valid? (constantly false)})
       {:id 1 :foo "bar" :baz "quuz"})))


(doseq [a [add-get-action edit-get-action view-one-action]]
  (noir.util.test/with-noir
    (a settings/defaults {:id 1})))
