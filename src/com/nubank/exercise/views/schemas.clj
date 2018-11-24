(ns com.nubank.exercise.views.schemas
  (:require [schema.core :as s]))

(s/defschema DinosaurSchema {:row (s/constrained s/Int #(<= 0 % 49))
                             :col (s/constrained s/Int #(<= 0 % 49))})

(s/defschema RobotSchema {:row (s/constrained s/Int #(<= 0 % 49))
                          :col (s/constrained s/Int #(<= 0 % 49))
                          :dirn (s/enum :north
                                        :west
                                        :south
                                        :east)})

(s/defschema ActionSchema (s/if #(= (:action %) :move)
                             {:action (s/eq :move) :param (s/enum :forward :backward)}
                             (s/if #(= (:action %) :turn)
                               {:action (s/eq :turn) :param (s/enum :left :right)}
                               {:action (s/eq :attack)})))
