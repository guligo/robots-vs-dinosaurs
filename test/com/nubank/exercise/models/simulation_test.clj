(ns com.nubank.exercise.models.simulation-test
  (:require [midje.sweet :refer :all]
            [com.nubank.exercise.models.simulation :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(facts "About simulation"
       (background (before :facts (delete-simulation!)))
       (fact "Simulation persists state"
             (do (create-robot! (robot 0 0 :north))
                 (get-simulation))
             => (simulation [(robot 0 0 :north 0)]))
       (fact "Possible to reset ongoing simulation"
             (do (create-robot! (robot 0 0 :north))
                 (delete-simulation!))
             => (simulation []))
       (fact "Dinosaur creation is delegated"
             (do (create-dinosaur! (dinosaur 3 3))
                 (get-simulation))
             => (simulation [(dinosaur 3 3 321)])
             (provided (create-dinosaur [] (let [dinosaur (dinosaur 3 3)] dinosaur))
                       => [(dinosaur 3 3 321)] :times 1))
       (fact "Robot creation is delegated"
             (do (create-robot! (robot 1 1 :east))
                 (get-simulation))
             => (simulation [(robot 1 1 :east 123)])
             (provided (create-robot [] (let [robot (robot 1 1 :east)] robot))
                       => [(robot 1 1 :east 123)] :times 1))
       (fact "Robot action is delegated"
             (do (create-robot! (robot 3 3 :west))
                 (perform-robot-action! (robot 3 3 :west 0) (action :move :forward))
                 (get-simulation))
             => (simulation [(robot 2 3 :west 0)])
             (provided (perform-robot-action [(robot 3 3 :west 0)]
                                             (let [robot (robot 3 3 :west 0)] robot)
                                             (let [action (action :move :forward)] action)) => [(robot 2 3 :west 0)] :times 1)))
