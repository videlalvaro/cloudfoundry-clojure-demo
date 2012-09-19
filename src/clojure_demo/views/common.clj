(ns clojure-demo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "Cloud Foundry Clojure Integration"]
               (include-css "/css/bootstrap.min.css"
                            "/css/bootstrap-responsive.min.css"
                            "/css/styles.css")]
              [:body
               [:div {:class "navbar navbar-inverse navbar-fixed-top"}
                [:div {:class "navbar-inner"}
                 [:div {:class "container-fluid"}
                  [:a {:class "btn btn-navbar"
                       :data-toggle "collapse"
                       :data-target ".nav-collapse"}
                   [:span {:class "icon-bar"}]
                   [:span {:class "icon-bar"}]
                   [:span {:class "icon-bar"}]]
                  [:a {:class "brand" :href "#"} "Clojure Demo"]
                  [:div {:class "nav-collapse collapse"}
                   [:p {:class "navbar-text pull-right"}
                    [:a {:href "http://cloudfoundry.com/"} "Cloud Foundry"]]
                   [:ul {:class "nav"}
                    [:li {:class "active"} [:a {:href "#"} "Home"]]
                    [:li [:a {:href "#about"} "About"]]]
                   ] ;;nav-collapse
                  ]]] ;; closes outer divs
               [:div {:class "container-fluid"}
                [:div {:class "row-fluid"}
                 content]]]))
