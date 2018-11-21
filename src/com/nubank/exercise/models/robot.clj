(ns com.nubank.exercise.models.robot
  (:require [com.nubank.exercise.models.common :refer :all]
            [com.nubank.exercise.models.simulation :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all])
  (:import (com.nubank.exercise.models.simulation Simulation)
           (com.nubank.exercise.models.dinosaur Dinosaur)))

(defrecord Robot [id, row, col, dirn])

(defn create-robot [simulation, robot]
  "Adds robot to simulation "
  (create-actor simulation robot))

(defn get-robot [simulation, robot-id]
  "Retrieves robot by its ID "
  (some #(if (= robot-id (:id %)) %) (:actors simulation)))

(defn update-robot [simulation, robot]
  "Updates robot in simulation based on its ID ",
  (def index
    (first
      (map
        first
        (filter
          #(= (:id (second %)) (:id robot))
          (map-indexed vector (:actors simulation))))))

  (if (some? index)
    (Simulation. (assoc (:actors simulation) index robot))
    simulation))

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
