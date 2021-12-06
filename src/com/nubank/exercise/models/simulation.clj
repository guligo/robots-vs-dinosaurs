(ns com.nubank.exercise.models.simulation
  "This namespace contains functions that are to be provided to external caller for work with robots versus dinosaurs
  simulation. Majority of functions in this namespace are mutating the internal state of application."
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [clojure.tools.logging :as log]))

(def actors
  "This variable holds current list of actors."
  (atom []))

(defn get-actors
  "This function returns current list of actors."
  []
  @actors)

(defn delete-actors!
  "This function empties current list of actors.
  It returns modified actor list."
  []
  (log/debug "Deleting simulation")
  (swap! actors (fn [_] [])))

(defn create-dinosaur!
  "This function creates dinosaur in current list of actors.
  It returns modified actor list."
  [dinosaur]
  (log/debug "Creating dinosaur" dinosaur)
  (swap! actors (fn [current-actors] (create-dinosaur current-actors dinosaur))))

(defn create-robot!
  "This function creates robot in current list of actors.
  It returns modified actor list."
  [robot]
  (log/debug "Creating robot" robot)
  (swap! actors (fn [current-actors] (create-robot current-actors robot))))

(defn perform-robot-action!
  "This function performs robot action thus updating current list of actors.
  It returns modified actor list."
  [robot-id action]
  (log/debug "Performing action" action "on robot with ID" robot-id)
  (swap! actors (fn [current-actors] (perform-robot-action current-actors robot-id action))))
