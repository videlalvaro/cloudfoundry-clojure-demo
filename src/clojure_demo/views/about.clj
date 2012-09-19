(ns clojure-demo.views.about
  (:require [clojure-demo.views.common :as common])
  (:use [noir.core :only [defpage defpartial]]
        hiccup.element))

(defpartial about []
  [:p ""]
  [:p "Created by: "
   (link-to "http://twitter.com/old_sound" "Alvaro Videla")
   " - "
   (mail-to "avidela@vmare.com")]
  [:p "Learn more aboutthe platform: "
   (link-to "http://cloudfoundry.com/"
            "Get Started with Cloud Foundry")]
  [:p "Browse this project code and learn more: "
   (link-to "https://github.com/videlalvaro/cloudfoundry-clojure-demo"
            "Cloud Foundry Clojure Demo")]
  [:p "Learn more about Clojure/Cloud Foundry Integration: "
   (link-to "https://github.com/videlalvaro/cloudfoundry-clojure-demo"
            "claude")])

(defpage "/about" []
  (common/layout "about"
   [:div {:class "span12"}
    [:div {:class "hero-unit"}
     [:h2 "About"]
     (about)
     ]]))