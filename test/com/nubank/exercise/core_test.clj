(ns com.nubank.exercise.core-test
  (:require [clojure.test :refer :all]
            [com.nubank.exercise.core :refer :all])
  (:import (com.nubank.exercise.core Simulation Cell Robot Dinosaur)))

(deftest adding-actors-to-simulation
  (testing "Robot is added to simulation"
    (is (= (Simulation. [(Robot. 1 (Cell. 0 0) :up)]) (create-robot (Simulation. []) (Robot. 1 (Cell. 0 0) :up)))))
  (testing "Dinosaur is added to simulation"
    (is (= (Simulation. [(Dinosaur. (Cell. 0 0))]) (create-dinosaur (Simulation. []) (Dinosaur. (Cell. 0 0)))))))

(deftest deleting-actors-from-simulation
  (testing "Dinosaur is removed from simulation"
    (is (= (Simulation. []) (delete-dinosaur (Simulation. [(Dinosaur. (Cell. 0 0))]) (Dinosaur. (Cell. 0 0))))))
  (testing "Robot is not removed from simulation when actually dinosaur is expected to be removed"
    (is (= (Simulation. [(Robot. 1 (Cell. 0 0) :up)]) (delete-dinosaur (Simulation. [(Robot. 1 (Cell. 0 0) :up)]) (Dinosaur. (Cell. 0 0)))))))
