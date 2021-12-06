(ns me.guligo.exercise.app
  "This namespace represents main application entry point."
  (:require [me.guligo.exercise.controllers.rest :as r]
            [me.guligo.exercise.controllers.page :as p]
            [compojure.api.sweet :refer :all]))

(defroutes app-routes
           "This macro builds handlers for REST API and HTTP pages."
           (p/page-routes)
           (r/rest-routes))

(def app app-routes)
