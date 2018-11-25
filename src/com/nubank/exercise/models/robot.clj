(ns com.nubank.exercise.models.robot
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(defn robot
  ([row, col, dirn] (assoc (actor :robot row col) :dirn dirn))
  ([row, col, dirn, id] (assoc (actor :robot row col id) :dirn dirn)))

(defn action
  ([name] {:action name})
  ([name, param] {:action name :param param}))

(defn create-robot [actors, robot]
  "Adds robot to simulation"
  (create-actor actors robot))

(defn- attack-dinosaurs [actors, robot]
  "Destroys dinosaurs around robot"
  (-> actors
      (delete-actor :dinosaur (dec (:row robot)) (:col robot))
      (delete-actor :dinosaur (inc (:row robot)) (:col robot))
      (delete-actor :dinosaur (:row robot) (dec (:col robot)))
      (delete-actor :dinosaur (:row robot) (inc (:col robot)))))

(defn- turn-robot [actors, robot, side]
  "Turns robot to the left or right"
  (let [dirn (cond
               (and (= side :left) (= (:dirn robot) :north)) :west
               (and (= side :left) (= (:dirn robot) :west)) :south
               (and (= side :left) (= (:dirn robot) :south)) :east
               (and (= side :left) (= (:dirn robot) :east)) :north
               (and (= side :right) (= (:dirn robot) :north)) :east
               (and (= side :right) (= (:dirn robot) :east)) :south
               (and (= side :right) (= (:dirn robot) :south)) :west
               (and (= side :right) (= (:dirn robot) :west)) :north)]
    (if (some? dirn)
      (update-actor actors (assoc robot :dirn dirn))
      actors)))

(defn- move-robot [actors, robot, motion]
  "Moves robot forward or backward"
  (let [move (cond
               (and (= motion :forward) (= (:dirn robot) :north)) {:row (dec (:row robot))}
               (and (= motion :forward) (= (:dirn robot) :west)) {:col (dec (:col robot))}
               (and (= motion :forward) (= (:dirn robot) :south)) {:row (inc (:row robot))}
               (and (= motion :forward) (= (:dirn robot) :east)) {:col (inc (:col robot))}
               (and (= motion :backward) (= (:dirn robot) :north)) {:row (inc (:row robot))}
               (and (= motion :backward) (= (:dirn robot) :west)) {:col (inc (:col robot))}
               (and (= motion :backward) (= (:dirn robot) :south)) {:row (dec (:row robot))}
               (and (= motion :backward) (= (:dirn robot) :east)) {:col (dec (:col robot))})]
    (update-actor actors (merge robot move))))

(defn perform-robot-action [actors, robot-id, action]
  "Performs specific action on robot"
  (let [robot (get-actor actors :robot robot-id)
        name (:action action)
        param (:param action)]
    (cond
      (and (some? robot) (= name :attack)) (attack-dinosaurs actors robot)
      (and (some? robot) (= name :turn)) (turn-robot actors robot param)
      (and (some? robot) (= name :move)) (move-robot actors robot param)
      :else actors)))
