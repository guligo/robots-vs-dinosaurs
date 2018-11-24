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
             (attack-dinosaurs [(dinosaur 0 0 0)
                                (dinosaur 0 1 1)
                                (dinosaur 0 2 2)
                                (dinosaur 2 0 3)
                                (dinosaur 2 1 4)
                                (dinosaur 2 2 5)
                                (dinosaur 1 0 6)
                                (robot 1 1 :south 7)
                                (dinosaur 1 2 8)]
                               (robot 1 1 :south 7))
             => [(dinosaur 0 0 0)
                 (dinosaur 0 2 2)
                 (dinosaur 2 0 3)
                 (dinosaur 2 2 5)
                 (robot 1 1 :south 7)])
       (fact "Attack does not destroy robot"
             (attack-dinosaurs [(robot 0 1 :west 0)
                                (dinosaur 1 0 1)
                                (robot 1 1 :east 2)]
                               (robot 1 1 :east 2))
             => [(robot 0 1 :west 0)
                 (robot 1 1 :east 2)])
       (fact "Attack does not fail if performed on the border"
             (attack-dinosaurs [(dinosaur 0 0 0)
                                (dinosaur 0 1 1)
                                (robot 1 0 :east 2)
                                (dinosaur 1 1 3)
                                (dinosaur 2 0 4)
                                (dinosaur 2 1 5)]
                               (robot 1 0 :east 2))
             => [(dinosaur 0 1 1)
                 (robot 1 0 :east 2)
                 (dinosaur 2 1 5)])
       (fact "Attack does not fail if performed when no actor is around"
             (attack-dinosaurs [(robot 49 49 :north 0)]
                               (robot 49 49 :north 0))
             => [(robot 49 49 :north 0)]))

(facts "About turning robot"
       (fact "Possible to turn robot left"
             (turn-robot [(robot 0 0 :north 0)] (robot 0 0 :north 0) :left)
             => [(robot 0 0 :west 0)])
       (fact "Possible to turn robot right"
             (turn-robot [(robot 5 5 :east 1)] (robot 5 5 :east 1) :right)
             => [(robot 5 5 :south 1)]))

(facts "About moving robot"
       (fact "Possible to move robot"
             (move-robot [(robot 24 24 :north 123)] (robot 24 24 :north 123) :forward)
             => [(robot 23 24 :north 123)])
       (fact "Impossible to move robot outside of border"
             (move-robot [(robot 0 0 :west 321)] (robot 0 0 :west 321) :forward)
             => [(robot 0 0 :west 321)])
       (fact "Impossible to move robot in place occupied by other actor"
             (move-robot [(robot 24 24 :north 123) (dinosaur 25 24 321)] (robot 24 24 :south 123) :backward)
             => [(robot 24 24 :north 123) (dinosaur 25 24 321)]))
