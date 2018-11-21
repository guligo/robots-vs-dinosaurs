(ns com.nubank.exercise.models.dinosaur
  (:require [com.nubank.exercise.models.common :refer :all]))

(defrecord Dinosaur [row, col])

(defn create-dinosaur [simulation, dinosaur]
  "Adds dinosaur to simulation "
  (create-actor simulation dinosaur))

(defn delete-dinosaur [simulation, dinosaur]
  "Deletes dinosaur from simulation "
  (if (instance? Dinosaur dinosaur)
    (delete-actor simulation dinosaur)
    simulation))
