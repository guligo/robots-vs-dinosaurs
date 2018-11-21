(ns com.nubank.exercise.models.robot-test
  (:require [clojure.test :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [com.nubank.exercise.models.simulation :refer :all])
  (:import (com.nubank.exercise.models.simulation Simulation)
           (com.nubank.exercise.models.robot Robot)
           (com.nubank.exercise.models.dinosaur Dinosaur)))

(deftest robot-turn
  (testing "Robot is turning"
    (is (= (Simulation. [(Robot. 1 1 1 :up)])
           (turn (Simulation. [(Robot. 1 1 1 :left)]) (Robot. 1 1 1 :left) :right)))))

(deftest robot-attack
  (testing "Robot is destroying dinosaurs around it"
    (is (= (Simulation. [(Robot. 1 1 1 :up)])
           (attack (Simulation. [(Robot. 1 1 1 :up), (Dinosaur. 1 0), (Dinosaur. 0 1), (Dinosaur. 2 1), (Dinosaur. 1 2)]) (Robot. 1 1 1 :up))))))
