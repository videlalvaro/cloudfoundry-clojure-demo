(ns clojure-demo.server
  (:gen-class)
  (:require [noir.server :as server]
            [claude.core :as cf]))

(server/load-views-ns 'clojure-demo.views)

(defn -main [& m]
  (let [mode (if (cf/cloudfoundry?) :prod :dev)
        port (Integer. (get (System/getenv) "VMC_APP_PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'clojure-demo})))
