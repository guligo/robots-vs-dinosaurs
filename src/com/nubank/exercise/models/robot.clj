(ns com.nubank.exercise.models.robot
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(defn robot
  ([row, col, dirn] (assoc (actor :robot row col) :dirn dirn))
  ([row, col, dirn, id] (assoc (actor :robot row col id) :dirn dirn)))

(defn action
  ([name] {:name name})
  ([name, param] {:name name :param param}))

(defn create-robot [actors, robot]
  "Adds robot to simulation"
  (create-actor actors robot))

(defn attack-dinosaurs [actors, robot]
  "Destroys dinosaurs around robot"
  (-> actors
      (delete-actor :dinosaur (- (:row robot) 1) (:col robot))
      (delete-actor :dinosaur (+ (:row robot) 1) (:col robot))
      (delete-actor :dinosaur (:row robot) (- (:col robot) 1))
      (delete-actor :dinosaur (:row robot) (+ (:col robot) 1))))

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
                   :west {:col (+ (:col robot) 1)}
                   :south {:row (+ (:row robot) 1)}
                   :east {:col (- (:col robot) 1)})
                 {}))]
    (update-actor actors (merge robot move))))

(defn perform-robot-action [actors, robot-id, action]
  "Performs specific action on robot"
  (let [robot (get-actor actors :robot robot-id)
        name (:action action)
        param (:param action)]
    (case name
      :attack (attack-dinosaurs actors robot)
      :turn (turn-robot actors robot param)
      :move (move-robot actors robot param)
      actors)))
