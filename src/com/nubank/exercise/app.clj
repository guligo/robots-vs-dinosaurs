(ns com.nubank.exercise.app
  "This namespace represents main application entry point."
  (:require [com.nubank.exercise.controllers.rest :as r]
            [com.nubank.exercise.controllers.page :as p]
            [compojure.api.sweet :refer :all]))

(defroutes app-routes
           "This macro builds handlers for REST API and HTTP pages."
           (p/page-routes)
           (r/rest-routes))

(def app app-routes)
