(ns com.nubank.exercise.views.dashboard
  "This namespace contains functions for rendering simulation dashboard page."
  (:require [com.nubank.exercise.models.common :refer :all]
            [com.nubank.exercise.models.simulation :refer :all]
            [hiccup.core :refer :all]))

(defn- render-direction
  "This function returns string representation of a direction."
  [dirn]
  (case dirn
    :north "^"
    :east ">"
    :south "v"
    :west "<"))

(defn- render-actor
  "This function returns HTML representation of an actor."
  [actor]
  (if (= (:type actor) :robot)
    [:span {:style "color: blue;"} "R" (:id actor) (render-direction(:dirn actor))]
    [:span {:style "color: green;"} "D" (:id actor)]))

(defn- render-field
  "This function returns HTML representation of an empty cell."
  []
  [:span {:style "color: silver;"} "---"])

(defn- render-cell
  "This function returns HTML representation of a cell."
  [actor-matrix row col]
  (if (contains? actor-matrix [row col])
    (let [actor (actor-matrix [row col])]
      (render-actor actor))
    (render-field)))

(defn render
  "This function returns HTML representation of simulation."
  []
  (let [actors (:actors (get-simulation))
        actor-matrix (zipmap (map #(vector (:row %) (:col %)) actors) actors)]
    (html
      [:div
       [:h1 "Simulation"]
       [:h2 "Dinosaur and robot count:&nbsp;" (count actors)]
       [:table
         (for [row (range min-boundary (inc max-boundary))]
           [:tr
           (for [col (range min-boundary (inc max-boundary))]
             [:td {:style "text-align: center;"} (render-cell actor-matrix row col)])])]])))
