(ns com.nubank.exercise.models.robot-test
  (:require [midje.sweet :refer :all]
            [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [com.nubank.exercise.models.robot :refer :all]))

(facts "About robot creation"
       (fact "Creation of robot actually means creation of an actor of 'robot' type with additional field"
             (create-robot [] (robot 48 49 :north))
             => [(assoc (actor :robot 48 49 0) :dirn :north)]))

(facts "About attack"
       (fact "Attack destroys dinosaurs in front, to the left, to the right or behind"
             (perform-robot-action [(dinosaur 0 0 0)
                                    (dinosaur 0 1 1)
                                    (dinosaur 0 2 2)
                                    (dinosaur 2 0 3)
                                    (dinosaur 2 1 4)
                                    (dinosaur 2 2 5)
                                    (dinosaur 1 0 6)
                                    (robot 1 1 :south 7)
                                    (dinosaur 1 2 8)]
                                   7
                                   (action :attack))
             => [(dinosaur 0 0 0)
                 (dinosaur 0 2 2)
                 (dinosaur 2 0 3)
                 (dinosaur 2 2 5)
                 (robot 1 1 :south 7)])
       (fact "Attack does not destroy robot"
             (perform-robot-action [(robot 0 1 :west 0)
                                    (dinosaur 1 0 1)
                                    (robot 1 1 :east 2)]
                                   2
                                   (action :attack))
             => [(robot 0 1 :west 0)
                 (robot 1 1 :east 2)])
       (fact "Attack does not fail if performed on the border"
             (perform-robot-action [(dinosaur 0 0 0)
                                    (dinosaur 0 1 1)
                                    (robot 1 0 :east 2)
                                    (dinosaur 1 1 3)
                                    (dinosaur 2 0 4)
                                    (dinosaur 2 1 5)]
                                   2
                                   (action :attack))
             => [(dinosaur 0 1 1)
                 (robot 1 0 :east 2)
                 (dinosaur 2 1 5)])
       (fact "Attack does not fail if performed when no actor is around"
             (perform-robot-action [(robot 49 49 :north 0)] 0 (action :attack))
             => [(robot 49 49 :north 0)])
       (fact "Attack cannot be performed by dinosaur"
             (perform-robot-action [(dinosaur 0 1 0) (dinosaur 1 1 1)] 1 (action :attack))
             => [(dinosaur 0 1 0) (dinosaur 1 1 1)])
       (fact "Attack cannot be performed by nonexistent robot"
             (perform-robot-action [(robot 0 0 :north 0)] 1 (action :attack))
             => [(robot 0 0 :north 0)]))

(facts "About turning"
       (fact "Robot needs parameter in order to turn"
             (perform-robot-action [(robot 1 2 :north 0)] 0 (action :turn))
             => [(robot 1 2 :north 0)])
       (fact "Robot can turn left"
             (perform-robot-action [(robot 10 20 :north 3)] 3 (action :turn :left))
             => [(robot 10 20 :west 3)]
             (perform-robot-action [(robot 10 20 :west 3)] 3 (action :turn :left))
             => [(robot 10 20 :south 3)]
             (perform-robot-action [(robot 10 20 :south 3)] 3 (action :turn :left))
             => [(robot 10 20 :east 3)]
             (perform-robot-action [(robot 10 20 :east 3)] 3 (action :turn :left))
             => [(robot 10 20 :north 3)])
       (fact "Robot can turn right"
             (perform-robot-action [(robot 5 5 :east 1)] 1 (action :turn :right))
             => [(robot 5 5 :south 1)]
             (perform-robot-action [(robot 5 5 :south 1)] 1 (action :turn :right))
             => [(robot 5 5 :west 1)]
             (perform-robot-action [(robot 5 5 :west 1)] 1 (action :turn :right))
             => [(robot 5 5 :north 1)]
             (perform-robot-action [(robot 5 5 :north 1)] 1 (action :turn :right))
             => [(robot 5 5 :east 1)])
       (fact "Dinosaur cannot turn"
             (perform-robot-action [(dinosaur 0 0 0)] 0 (action :turn :right))
             => [(dinosaur 0 0 0)])
       (fact "Nonexistent robot cannot turn"
             (perform-robot-action [(robot 0 0 :north 0)] 1 (action :turn :right))
             => [(robot 0 0 :north 0)])
       (fact "Robot can attack and then turn"
             (perform-robot-action (perform-robot-action [(robot 0 0 :north 0)] 0 (action :attack)) 0 (action :turn :right))
             => [(robot 0 0 :east 0)]))

(facts "About moving robot"
       (fact "Robot needs parameter in order to move"
             (perform-robot-action [(robot 1 2 :north 0)] 0 (action :move))
             => [(robot 1 2 :north 0)])
       (fact "Robot can move"
             (perform-robot-action [(robot 24 24 :north 123)] 123 (action :move :forward))
             => [(robot 23 24 :north 123)]
             (perform-robot-action [(robot 24 24 :east 123)] 123 (action :move :forward))
             => [(robot 24 25 :east 123)]
             (perform-robot-action [(robot 24 24 :south 123)] 123 (action :move :forward))
             => [(robot 25 24 :south 123)]
             (perform-robot-action [(robot 24 24 :west 123)] 123 (action :move :forward))
             => [(robot 24 23 :west 123)]
             (perform-robot-action [(robot 24 24 :north 123)] 123 (action :move :backward))
             => [(robot 25 24 :north 123)]
             (perform-robot-action [(robot 24 24 :east 123)] 123 (action :move :backward))
             => [(robot 24 23 :east 123)]
             (perform-robot-action [(robot 24 24 :south 123)] 123 (action :move :backward))
             => [(robot 23 24 :south 123)]
             (perform-robot-action [(robot 24 24 :west 123)] 123 (action :move :backward))
             => [(robot 24 25 :west 123)])
       (fact "Robot cannot move outside of border"
             (perform-robot-action [(robot 0 0 :west 321)] 321 (action :move :forward))
             => [(robot 0 0 :west 321)])
       (fact "Robot cannot move in place occupied by other actor"
             (perform-robot-action [(robot 24 24 :north 123) (dinosaur 25 24 321)] 123 (action :move :backward))
             => [(robot 24 24 :north 123) (dinosaur 25 24 321)])
       (fact "Dinosaur cannot move"
             (perform-robot-action [(dinosaur 0 0 0)] 0 (action :move :forward))
             => [(dinosaur 0 0 0)])
       (fact "Nonexistent robot cannot move"
             (perform-robot-action [(robot 0 0 :north 0)] 1 (action :move :forward))
             => [(robot 0 0 :north 0)])
       (fact "Robot can attack and then move"
             (perform-robot-action (perform-robot-action [(robot 0 0 :north 0)] 0 (action :attack)) 0 (action :move :backward))
             => [(robot 1 0 :north 0)]))
