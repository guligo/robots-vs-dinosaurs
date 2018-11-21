(ns com.nubank.exercise.views.schemas
  (:require [schema.core :as s]))

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
