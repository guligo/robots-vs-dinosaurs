(ns com.nubank.exercise.models.simulation
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(defn simulation [actors]
  {:actors actors})

(def ongoing-simulation (atom (simulation [])))

(defn get-simulation []
  "Retrieves ongoing simulation state"
  @ongoing-simulation)

(defn delete-simulation! []
  "Deletes ongoing simulation"
  (reset! ongoing-simulation (simulation [])))

(defn create-robot! [actor]
  "Creates robot in ongoing simulation"
  (reset! ongoing-simulation (simulation (create-robot (:actors @ongoing-simulation) actor))))

(defn create-dinosaur! [actor]
  "Creates dinosaur in ongoing simulation"
  (reset! ongoing-simulation (simulation (create-dinosaur (:actors @ongoing-simulation) actor))))
