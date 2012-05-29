(defproject crudite "0.1.0-SNAPSHOT"
  :description "A CRUD form generator for Noir"
  :url "http://github.com/kenrestivo/crudite"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [noir "1.3.0-beta7"]]
  :dev-dependencies [[clj-http "0.4.1"] ;; needs to be explicit!?
                     [com.cemerick/url "0.0.6"]
                     ]
  :profiles {:dev {:dependencies [[clj-http "0.4.1"] ;; needs to be explicit!?
                                  ]}})

