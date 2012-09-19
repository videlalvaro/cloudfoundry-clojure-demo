(ns clojure-demo.views.services
  (:require [clojure-demo.views.common :as common]
            [claude mongodb rabbitmq postgresql redis blob mysql])
  (:use [noir.core :only [defpage defpartial]]
        claude.core))

(defn get-service-prop [service prop]
  ((ns-resolve service (symbol prop))))

(defpartial service-prop [service prop value]
  [:dt [:code (str "(" service "/" prop ") => ")]]
  [:dd [:code value]])

(defpartial service-info [service props]
  [:div {:class "row-fluid"}
   [:div
    [:h2 (second (clojure.string/split (str service) #"\."))]
    [:p
     [:dl {:class "dl-horizontal"}
      (for [p props]
        (service-prop service p (get-service-prop service p)))]]]])

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
                 "username" "password"]))

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

(defpage "/" []
  (common/layout
   [:div {:class "span12"}
    [:div {:class "hero-unit"}
     [:h1 "Clojure - Cloud Foundry"]
     [:p "Explaing Clojure usage in Cloud Foundry"]
     [:p [:a {:class "btn btn-primary btn-large"} "Learn More"]]
     ] ;; hero-unit
    (mongodb-info)
    (rabbitmq-info)
    (mysql-info)
    (postgresql-info)
    (redis-info)
    (blob-info)]))
