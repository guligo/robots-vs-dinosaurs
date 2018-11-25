(ns com.nubank.exercise.views.dashboard
  (:require [com.nubank.exercise.models.common :refer :all]
            [com.nubank.exercise.models.simulation :refer :all]
            [hiccup.core :refer :all]))

(defn- render-direction [dirn]
  (case dirn
    :north "^"
    :east ">"
    :south "v"
    :west "<"))

(defn- render-actor [actor]
  (if (= (:type actor) :robot)
    [:span {:style "color: blue;"} "R" (:id actor) (render-direction(:dirn actor))]
    [:span {:style "color: green;"} "D" (:id actor)]))

(defn- render-field []
  [:span {:style "color: silver;"} "---"])

(defn- render-cell [matrix, row, col]
  (if (contains? matrix [row col])
    (let [actor (matrix [row col])]
      (render-actor actor))
    (render-field)))

(defn render []
  (let [actors (:actors (get-simulation))
        matrix (zipmap (map #(vector (:row %) (:col %)) actors) actors)]
    (html
      [:div
       [:h1 "Simulation"]
       [:h2 "Dinosaur and robot count:&nbsp;" (count actors)]
       [:table
         (for [row (range min-boundary (inc max-boundary))]
           [:tr
           (for [col (range min-boundary (inc max-boundary))]
             [:td {:style "text-align: center;"} (render-cell matrix row col)])])]])))
