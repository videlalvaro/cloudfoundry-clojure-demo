(ns clojure-demo.views.services
  (:require [clojure-demo.views.common :as common]
            [noir.content.getting-started]
            [claude mongodb rabbitmq postgresql redis blob mysql])
  (:use [noir.core :only [defpage defpartial]]
        claude.core))

(defn mock-vcap-env []
  (constantly (slurp (clojure.java.io/resource "json/1.json"))))

(defn get-service-prop [service prop]
  ((ns-resolve service (symbol prop))))

(defpartial service-prop [prop value]
  [:dt prop]
  [:dd value])

(defpartial service-info [service props]
  [:div
   [:h3 (str "Service: " service)]
   [:dl (for [p props]
          (service-prop p (get-service-prop service p)))]])

(defpartial mongodb-info []
  (service-info 'claude.mongodb
                ["host" "port" "username" "password"
                 "name" "db" "hostname" "url"]))

(defpartial blob-info []
  (service-info 'claude.blob
                ["hostname" "host" "port" "username" "password" "name"]))

(defpartial mysql-info []
  (service-info 'claude.mysql
                ["name" "hostname" "host" "port" "user"
                 "username" "password" "db" "url"]))

(defpartial rabbitmq-info []
  (service-info 'claude.rabbitmq
                ["name" "hostname" "host" "port" "vhost"
                 "username" "user" "password" "pass" "url"]))

(defpartial redis-info []
  (service-info 'claude.redis
                ["hostname" "host" "port" "password" "name"]))

(defpartial postgresql-info []
  (service-info 'claude.postgresql
                ["name" "host" "hostname" "port" "user"
                 "username" "password"]))

(defpage "/services" []
  (with-redefs [get-vcap-env (mock-vcap-env)]
    (common/layout [:div
                    (mongodb-info)
                    (rabbitmq-info)
                    (mysql-info)
                    (postgresql-info)
                    (redis-info)
                    (blob-info)
                    ])))
