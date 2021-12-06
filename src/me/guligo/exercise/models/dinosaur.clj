(ns me.guligo.exercise.models.dinosaur
  "This namespace contains dinosaur-specific functionality."
  (:require [me.guligo.exercise.models.core :refer :all]))

(defn dinosaur
  "This function constructs an actor of dinosaur type."
  ([row col] (actor :dinosaur row col))
  ([row col id] (actor :dinosaur row col id)))

(defn create-dinosaur
  "This function adds dinosaur to list of actors.
  It returns updated list of actors."
  [actors dinosaur]
  (create-actor actors dinosaur))

(defn delete-dinosaur
  "This function deletes dinosaur from list of actors.
  It returns updated list of actors."
  [actors row col]
  (delete-actor actors :dinosaur row col))
