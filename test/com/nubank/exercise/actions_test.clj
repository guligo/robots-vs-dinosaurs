(ns com.nubank.exercise.actions-test
  (:require [clojure.test :refer :all]
            [com.nubank.exercise.core :refer :all]
            [com.nubank.exercise.actions :refer :all])
  (:import (com.nubank.exercise.core Simulation Cell Robot Dinosaur)))

(deftest robot-attack
  (testing "Robot is destroying dinosaurs around it"
    (is (= (Simulation. [(Robot. 1 (Cell. 1 1) :up)])
           (attack (Simulation. [(Robot. 1 (Cell. 1 1) :up), (Dinosaur. (Cell. 1 0)), (Dinosaur. (Cell. 0 1)), (Dinosaur. (Cell. 2 1)), (Dinosaur. (Cell. 1 2))]) (Robot. 1 (Cell. 1 1) :up))))))
