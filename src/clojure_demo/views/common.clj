(ns clojure-demo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "clojure-demo"]
               (include-css "/css/reset.css" "/css/bootstrap.css")]
              [:body
               [:div#wrapper
                content]]))
