(ns com.nubank.exercise.app
  (:require [com.nubank.exercise.controllers.rest :as r]
            [com.nubank.exercise.controllers.page :as p]
            [compojure.api.sweet :refer :all]))

(defroutes app-routes
           (p/page-routes)
           (r/rest-routes))

(def app app-routes)
