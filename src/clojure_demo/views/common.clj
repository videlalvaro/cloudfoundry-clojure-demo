(ns clojure-demo.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial analytics []
  [:script {:type "text/javascript"}
   "var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-34953372-1']);
  _gaq.push(['_trackPageview']);

  (function() {
               var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
               ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
               var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
               })();"])

(defpartial layout [active & content]
            (html5
              [:head
               [:title "Cloud Foundry Clojure Integration"]
               (include-css "/css/bootstrap.min.css"
                            "/css/bootstrap-responsive.min.css"
                            "/css/styles.css")
               (analytics)]
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
                  [:a {:class "brand" :href "/"} "Clojure Demo"]
                  [:div {:class "nav-collapse collapse"}
                   [:p {:class "navbar-text pull-right"}
                    [:a {:href "http://cloudfoundry.com/"} "Cloud Foundry"]]
                   [:ul {:class "nav"}
                    [:li {:class (if (= "home" active) "active" "")} [:a {:href "/"} "Home"]]
                    [:li {:class (if (= "about" active) "active" "")} [:a {:href "/about"} "About"]]]
                   ] ;;nav-collapse
                  ]]] ;; closes outer divs
               [:div {:class "container-fluid"}
                [:div {:class "row-fluid"}
                 content]]]))
