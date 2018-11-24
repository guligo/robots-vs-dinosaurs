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

(defn create-dinosaur! [dinosaur]
  "Creates dinosaur in ongoing simulation"
  (reset! ongoing-simulation (simulation (create-dinosaur (:actors @ongoing-simulation) dinosaur))))

(defn create-robot! [robot]
  "Creates robot in ongoing simulation"
  (reset! ongoing-simulation (simulation (create-robot (:actors @ongoing-simulation) robot))))

(defn perform-robot-action! [robot-id, action]
  "Performs robot action thus updating ongoing simulation"
  (reset! ongoing-simulation (simulation (perform-robot-action (:actors @ongoing-simulation) robot-id action))))
