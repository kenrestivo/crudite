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


  12934:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/welcome"))
  12943:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/things"))
  12952:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1"))
  12961:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1/edit"))
  12989:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1/edit"))
  13017:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1/edit"))
  13045:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1/edit"))
  13073:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/1/edit"))
  13082:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/add"))
  13093:test-crudite.server=> (pprint (spewsave client/get "http://localhost:8081/thing/add"))
  13102:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/add" {:form-params {:foo "bar" :baz "quuz"}}))
  13111:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/update" {:form-params {:foo "bar" :baz "quuz"}}))
  13137:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/1/update" {:form-params {:foo "bar" :baz "quuz"}}))
  13165:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/1/update" {:form-params {:foo "bar" :baz "quuz"}}))
  13178:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/1/update" {:form-params {:foo "bar" :baz "quuz"}}))
  13187:test-crudite.server=> (pprint (spewsave client/post "http://localhost:8081/thing/1/update" {:form-params {:foo "bar" :baz "quuz"}}))
