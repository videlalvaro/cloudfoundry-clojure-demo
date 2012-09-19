(ns clojure-demo.views.services
  (:require [clojure-demo.views.common :as common]
            [claude mongodb rabbitmq postgresql redis blob mysql])
  (:use [noir.core :only [defpage defpartial]]
        hiccup.element
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

(defpartial reasons []
  [:p ""]
  [:p "We have created a library called "
   (link-to "https://github.com/videlalvaro/claude" "claude")
   " that lets you use "
   (link-to "http://clojure.org/" "Clojure")
   " in "
   (link-to "http://cloudfoundry.com/" "Cloud Foundry")
   ". This library is not official and is a first step into supporting "
   "Clojure in Cloud Foundry."]
  [:p "To use it simply add the following to your lein project dependencies: "
   [:code "[claude \"0.2.1-snapshot\"]"]
   " and then you'll be able to access directly in your code the credentials for the services
bound to your application."]
  [:p [:em "Note that this library only returns the information for the first"
       " service of its kind. This is a limitation of this library not of Cloud Foundry."]]
  [:p "To learn more how to deploy your "
   [:strong "Clojure"]
   " apps to "
   [:strong "Cloud Foundry"]
   " please visit the project page: "
   (link-to "https://github.com/videlalvaro/cloudfoundry-clojure-demo"
            "Cloud Foundry Clojure Demo")]
  [:p "Bellow you have an example of the available functions and what they return for
each of the services bound to this particular app. The data you see there is real from this website."])

(defpage "/" []
  (common/layout "home"
   [:div {:class "span12"}
    [:div {:class "hero-unit"}
     [:h1 "Clojure in Cloud Foundry"]
     (reasons)
     ] ;; hero-unit
    (mongodb-info)
    (rabbitmq-info)
    (mysql-info)
    (postgresql-info)
    (redis-info)
    (blob-info)]))
