(ns crudite.mock
  (:use clojure.test
        crudite.core)
  (:require [clj-http.client :as client]))


(client/get "http://localhost:8081/thing/1"))
(client/get "http://localhost:8081/thing/1/edit"))
(client/get "http://localhost:8081/thing/add"))
(client/get "http://localhost:8081/things"))
(client/get "http://localhost:8081/welcome"))
(client/post "http://localhost:8081/thing/1/update" {:form-params {:foo "bar" :baz "quuz"}}))
(client/post "http://localhost:8081/thing/add" {:form-params {:foo "bar" :baz "quuz"}}))

