(ns com.nubank.exercise.views.data
  (:require [schema.core :as s]))

(defrecord Simulation [actors])

(defrecord Robot [id, row, col, dirn])

(defrecord Dinosaur [row, col])

;; unfortunately Compojure API does not work with s/defrecord, hence schema is explicitly defined here

(s/defschema RobotSchema {:id s/Int
                          :row s/Int
                          :col s/Int
                          :dirn (s/enum :north
                                        :west
                                        :south
                                        :east)})

(s/defschema DinosaurSchema {:row s/Int
                             :col s/Int})

(s/defschema ActionSchema {:action (s/enum :move
                                           :turn
                                           :attack)
                           (s/optional-key :param) (s/enum :forward
                                                           :backward
                                                           :left
                                                           :right)})
