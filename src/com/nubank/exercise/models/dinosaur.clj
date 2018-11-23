(ns com.nubank.exercise.models.dinosaur
  (:require [com.nubank.exercise.models.core :refer :all]))

(defn dinosaur
  ([row, col] (actor :dinosaur row col))
  ([row, col, id] (actor :dinosaur row col id)))

(defn create-dinosaur [actors, dinosaur]
  "Adds dinosaur to simulation"
  (create-actor actors dinosaur))

(defn delete-dinosaur [actors, row, col]
  "Deletes dinosaur from simulation"
  (delete-actor actors :dinosaur row col))
