(ns com.nubank.exercise.views.dashboard
  (:require [hiccup.core :refer :all]))

(defn page [req]
  (html
    [:div
     [:h1 "Simulation"]
     [:p "Content"]]))
