(ns com.nubank.exercise.controllers.page
  "This namespace contains functions used for creation of HTTP routes for HTML pages."
  (:require [com.nubank.exercise.views.dashboard :refer :all]
            [compojure.api.sweet :refer :all]))

(defn page-routes
  "This function creates HTTP routes for HTML pages."
  []
  (GET "/dashboard" _ (render)))
