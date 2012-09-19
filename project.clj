(defproject clojure-demo "0.1.0-SNAPSHOT"
  :description "Clojure - Cloud Foundry demo app"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta3"]
                 [claude "0.2.1-SNAPSHOT"]]
  :profiles {:dev {:resource-paths ["test/resources"]}}
  :main clojure-demo.server
  :plugins [[lein-swank "1.4.4"]])
