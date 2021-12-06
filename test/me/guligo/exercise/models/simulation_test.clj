(ns me.guligo.exercise.models.simulation-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.models.robot :refer :all]
            [me.guligo.exercise.models.dinosaur :refer :all]
            [me.guligo.exercise.models.simulation :refer :all]))

(facts "About simulation"
       (background (after :facts (delete-actors!)))
       (fact "Simulation persists state"
             (do (create-robot! (robot 0 0 :north))
                 (get-actors))
             => [(robot 0 0 :north 0)])
       (fact "Possible to reset ongoing simulation"
             (do (create-robot! (robot 0 0 :north))
                 (delete-actors!)
                 (get-actors)
             => []))
       (fact "Dinosaur creation is delegated"
             (do (create-dinosaur! (dinosaur 3 3))
                 (get-actors))
             => [(dinosaur 3 3 321)]
             (provided (create-dinosaur [] (let [dinosaur (dinosaur 3 3)] dinosaur))
                       => [(dinosaur 3 3 321)] :times 1))
       (fact "Robot creation is delegated"
             (do (create-robot! (robot 1 1 :east))
                 (get-actors))
             => [(robot 1 1 :east 123)]
             (provided (create-robot [] (let [robot (robot 1 1 :east)] robot))
                       => [(robot 1 1 :east 123)] :times 1))
       (fact "Robot action is delegated"
             (do (create-robot! (robot 3 3 :west))
                 (perform-robot-action! 0 (action :move :forward))
                 (get-actors))
             => [(robot 3 2 :west 0)]
             (provided (perform-robot-action [(robot 3 3 :west 0)]
                                             0
                                             (let [action (action :move :forward)] action)) => [(robot 3 2 :west 0)] :times 1)))
