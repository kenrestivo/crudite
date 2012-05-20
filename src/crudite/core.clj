(ns crudite.core
  (:use
   [hiccup.core :only [html]]
   [hiccup.form :only [form-to label submit-button
                       text-field file-upload drop-down]]
   [noir.response :only [redirect]]
   [noir.core :only [defpage defpartial render]])
  (:require
   [noir.session :as session]
   [noir.validation :as validation]))



(defmacro defpage-hack
  "Wraps noir.core/defpage, after eval'ing the url argument,
    so that you can pass the url as (str something something) or (:keyname m)
    instead of a static string or vector."
  [url des & all]
  (let [cleaned (eval url)]
  `(defpage ~cleaned ~des ~@all)))