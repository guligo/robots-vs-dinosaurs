(ns com.nubank.exercise.models.actions
  (:require [com.nubank.exercise.models.core :refer :all])
  (:import (com.nubank.exercise.views.data Robot Dinosaur)))

(defn move[simulation, robot, dirn]
  "Moves robot forward or backward"
  simulation)

(defn turn[simulation, robot, side]
  "Turns robot to the left or right"
  (def new-side (if (= side :left)
    (case (:dirn robot)
      :up :left
      :left :down
      :down :right
      :right :up)
    (case (:dirn robot)
      :up :right
      :right :down
      :down :left
      :left :up)))
  (update-robot simulation (Robot. (:id robot) (:row robot) (:col robot) new-side)))

(defn attack[simulation, robot]
  "Destroys dinosaurs around robot"
  (-> simulation
    (delete-dinosaur (Dinosaur. (- (:row robot) 1) (:col robot)))
    (delete-dinosaur (Dinosaur. (+ (:row robot) 1) (:col robot)))
    (delete-dinosaur (Dinosaur. (:row robot) (- (:col robot) 1)))
    (delete-dinosaur (Dinosaur. (:row robot) (+ (:col robot) 1)))
  ))
