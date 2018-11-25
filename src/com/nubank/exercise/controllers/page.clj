(ns com.nubank.exercise.controllers.page
  (:require [com.nubank.exercise.views.dashboard :refer :all]
            [compojure.api.sweet :refer :all]))

(defn page-routes []
  (GET "/dashboard" _ (render)))
