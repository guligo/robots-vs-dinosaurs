(ns com.nubank.exercise.actions
  (:require [com.nubank.exercise.core :refer :all])
  (:import (com.nubank.exercise.core Cell Robot Dinosaur)))

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
  (update-robot simulation (Robot. (:id robot) (:pos robot) new-side)))

(defn attack[simulation, robot]
  "Destroys dinosaurs around robot"
  (delete-dinosaur
    (delete-dinosaur
      (delete-dinosaur
        (delete-dinosaur simulation, (Dinosaur. (Cell. (- (:row (:pos robot)) 1) (:col (:pos robot)))))
        (Dinosaur. (Cell. (+ (:row (:pos robot)) 1) (:col (:pos robot)))))
      (Dinosaur. (Cell. (:row (:pos robot)) (- (:col (:pos robot)) 1))))
    (Dinosaur. (Cell. (:row (:pos robot)) (+ (:col (:pos robot)) 1)))))
