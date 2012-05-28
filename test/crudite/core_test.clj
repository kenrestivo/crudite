(ns crudite.core-test
  (:use clojure.test
        crudite.core))

;; TODO: this test is a bit too dependent on noir implementation details. 
(deftest post-fubarbaz
  (defpage-hack  [:post (str "/fubar" "/baz")]  {:as fields} fields)
  (is (= POST--fubar--baz (:POST--fubar--baz @noir.core/route-funcs))))

(deftest get-fubarbaz
  (defpage-hack  (str "/fubar" "/baz")  {:as fields} fields)
  (is (= GET--fubar--baz (:GET--fubar--baz @noir.core/route-funcs))))



