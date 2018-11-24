(ns com.nubank.exercise.models.robot
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(defn robot
  ([row, col, dirn] (assoc (actor :robot row col) :dirn dirn))
  ([row, col, dirn, id] (assoc (actor :robot row col id) :dirn dirn)))

(defn create-robot [actors, robot]
  "Adds robot to simulation"
  (create-actor actors robot))

;; TODO: consider refactoring into form attack-dinosaurs [actors, robot-id]

(defn attack-dinosaurs [actors, robot]
  "Destroys dinosaurs around robot"
  (-> actors
      (delete-actor :dinosaur (- (:row robot) 1) (:col robot))
      (delete-actor :dinosaur (+ (:row robot) 1) (:col robot))
      (delete-actor :dinosaur (:row robot) (- (:col robot) 1))
      (delete-actor :dinosaur (:row robot) (+ (:col robot) 1))))

;; TODO: consider refactoring into form turn-robot [actors, robot-id, side]

(defn turn-robot [actors, robot, side]
  "Turns robot to the left or right"
  (let [dirn (if (= side :left)
               (case (:dirn robot)
                 :north :west
                 :west :south
                 :south :east
                 :east :north)
               (if (= side :right)
                 (case (:dirn robot)
                   :north :east
                   :east :south
                   :south :west
                   :west :north)
                 side))]
    (update-actor actors (assoc robot :dirn dirn))))

;; TODO: consider refactoring into form move-robot [actors, robot-id, motion]

(defn move-robot [actors, robot, motion]
  "Moves robot forward or backward"
  (let [move (if (= motion :forward)
               (case (:dirn robot)
                 :north {:row (- (:row robot) 1)}
                 :west {:col (- (:col robot) 1)}
                 :south {:row (+ (:row robot) 1)}
                 :east {:col (+ (:col robot) 1)})
               (if (= motion :backward)
                 (case (:dirn robot)
                   :north {:row (- (:row robot) 1)}
                   :west {:col (- (:col robot) 1)}
                   :south {:row (+ (:row robot) 1)}
                   :east {:col (+ (:col robot) 1)})
                 {}))]
    (update-actor actors (merge robot move))))
