(ns com.nubank.exercise.models.robot
  "This namespace contains robot-specific functionality."
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(defn robot
  "This function constructs an actor of robot type with additional properties."
  ([row col dirn] (assoc (actor :robot row col) :dirn dirn))
  ([row col dirn id] (assoc (actor :robot row col id) :dirn dirn)))

(defn action
  "This function constructs map that holds parameters for robot's action."
  ([name] {:action name})
  ([name param] {:action name :param param}))

(defn create-robot
  "This function adds robot to list of actors.
  It returns updated list of actors."
  [actors robot]
  (create-actor actors robot))

(defn- attack-dinosaurs
  "This function destroys dinosaurs around robot (in front, in the back, on the left and on the right).
  It returns updated list of actors."
  [actors robot]
  (-> actors
      (delete-actor :dinosaur (dec (:row robot)) (:col robot))
      (delete-actor :dinosaur (inc (:row robot)) (:col robot))
      (delete-actor :dinosaur (:row robot) (dec (:col robot)))
      (delete-actor :dinosaur (:row robot) (inc (:col robot)))))

(defn- turn-robot
  "This function turns robot to the left or right.
  It returns updated list of actors."
  [actors robot side]
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

(defn- move-robot
  "This function moves robot forward or backward.
  It returns updated list of actors."
  [actors robot motion]
  (let [move (cond
               (and (= motion :forward) (= (:dirn robot) :north)) {:row (dec (:row robot))}
               (and (= motion :forward) (= (:dirn robot) :west)) {:col (dec (:col robot))}
               (and (= motion :forward) (= (:dirn robot) :south)) {:row (inc (:row robot))}
               (and (= motion :forward) (= (:dirn robot) :east)) {:col (inc (:col robot))}
               (and (= motion :backward) (= (:dirn robot) :north)) {:row (inc (:row robot))}
               (and (= motion :backward) (= (:dirn robot) :west)) {:col (inc (:col robot))}
               (and (= motion :backward) (= (:dirn robot) :south)) {:row (dec (:row robot))}
               (and (= motion :backward) (= (:dirn robot) :east)) {:col (dec (:col robot))})]
    (if (some? move)
      (update-actor actors (merge robot move))
      actors)))

(defn perform-robot-action
  "This function performs requested robot action.
  It returns updated list of actors."
  [actors robot-id action]
  (let [robot (get-actor actors :robot robot-id)
        name (:action action)
        param (:param action)]
    (cond
      (and (some? robot) (= name :attack)) (attack-dinosaurs actors robot)
      (and (some? robot) (= name :turn)) (turn-robot actors robot param)
      (and (some? robot) (= name :move)) (move-robot actors robot param)
      :else actors)))
