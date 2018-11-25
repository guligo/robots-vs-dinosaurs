(ns com.nubank.exercise.models.dinosaur
  "This namespace contains dinosaur-specific functionality."
  (:require [com.nubank.exercise.models.core :refer :all]))

(defn dinosaur
  "This function constructs an actor of dinosaur type."
  ([row col] (actor :dinosaur row col))
  ([row col id] (actor :dinosaur row col id)))

(defn create-dinosaur
  "This function adds dinosaur to list of actors."
  [actors dinosaur]
  (create-actor actors dinosaur))

(defn delete-dinosaur
  "This function deletes dinosaur from list of actors."
  [actors row col]
  (delete-actor actors :dinosaur row col))
