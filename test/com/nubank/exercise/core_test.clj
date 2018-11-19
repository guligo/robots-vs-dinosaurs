(ns com.nubank.exercise.core-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [com.nubank.exercise.core :refer :all])
  (:import (com.nubank.exercise.data Simulation Robot Dinosaur)))

(facts "About robot creation"
  (fact "Robot can be added to simulation"
    (Simulation. [(Robot. 1 0 0 :up)])
    => (create-robot (Simulation. []) (Robot. 1 0 0 :up))))

(deftest creating-actors-in-simulation
  (testing "Robots are added to simulation"
    (is (= (Simulation. [(Robot. 1 0 0 :up), (Robot. 2 1 1 :down)])
           (-> (Simulation. [])
               (create-robot (Robot. 1 0 0 :up))
               (create-robot (Robot. 2 1 1 :down))))))

  (testing "Dinosaur is added to simulation"
    (is (= (Simulation. [(Dinosaur. 0 0)])
           (create-dinosaur (Simulation. []) (Dinosaur. 0 0)))))

  (testing "Robot and dinosaur are added to simulation"
    (is (= (Simulation. [(Robot. 1 0 0 :up), (Dinosaur. 1 1)])
           (-> (Simulation. [])
               (create-robot (Robot. 1 0 0 :up))
               (create-dinosaur (Dinosaur. 1 1))))))

  (testing "Robot is not added to simulation if an actor already exists in that spot"
    (is (= (Simulation. [(Dinosaur. 0 0)])
           (create-robot (Simulation. [(Dinosaur. 0 0)]) (Robot. 1 0 0 :up))))))

(deftest retrieving-actors-from-simulation
  (testing "Robot is retrieved from simulation by its ID"
    (is (= (Robot. 2 1 1 :down)
           (get-robot (Simulation. [(Robot. 1 0 0 :up), (Robot. 2 1 1 :down), (Robot. 3 2 2 :left)]) 2)))))

(deftest updating-actors-in-simulation
  (testing "Robot is updated if robot with existing ID is found"
    (is (= (Simulation. [(Robot. 1 0 1 :down)]),
           (update-robot (Simulation. [(Robot. 1 0 0 :right)]) (Robot. 1 0 1 :down)))))

  (testing "Robot is not updated if robot with existing ID is not found"
    (is (= (Simulation. [(Robot. 1 0 0 :right)]),
           (update-robot (Simulation. [(Robot. 1 0 0 :right)]) (Robot. 2 0 1 :down))))))

(deftest deleting-actors-from-simulation
  (testing "Dinosaur is removed from simulation"
    (is (= (Simulation. [])
           (delete-dinosaur (Simulation. [(Dinosaur. 0 0)]) (Dinosaur. 0 0)))))

  (testing "Robot is not removed from simulation when actually dinosaur is expected to be removed"
    (is (= (Simulation. [(Robot. 1 0 0 :up)])
           (delete-dinosaur (Simulation. [(Robot. 1 0 0 :up)]) (Dinosaur. 0 0))))))
