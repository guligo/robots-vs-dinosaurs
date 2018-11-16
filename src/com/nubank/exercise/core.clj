(ns com.nubank.exercise.core
  (:import (com.sun.xml.internal.bind.v2.model.core ID)))

(defrecord Cell [row, col])

(defrecord Robot [id, pos, dirn])

(defrecord Dinosaur [pos])

(defrecord Simulation [actors])

(defn- private-create-actor [simulation, actor]
  "Adds an actor to simulation"
  (if (not-any? #(and (= (:pos :row %) (:pos :row actor)) (= (:pos :col %) (:pos :col actor))) (:actors simulation))
    (->Simulation (conj (:actors simulation) actor))
    simulation))

(defn- private-delete-actor [simulation, actor]
  " Deletes an actor from simulation "
  (->Simulation (remove #(= actor %) (:actors simulation))))

(defn create-robot [simulation, robot]
  " Adds robot to simulation "
  (private-create-actor simulation robot))

(defn get-robot [simulation, robot-id]
  " Retrieves robot by its ID "
  (some #(if (= robot-id (:id %)) %) (:actors simulation)))

(defn create-dinosaur [simulation, dinosaur]
  " Adds dinosaur to simulation "
  (private-create-actor simulation dinosaur))

(defn update-robot [simulation, robot]
  " Updates robot in simulation based on its ID ",
  (def index
    (first
      (map
        first
        (filter
          #(= (:id (second %)) (:id robot))
          (map-indexed vector (:actors simulation))))))

  (if (some? index)
    (->Simulation (assoc (:actors simulation) index robot))
    simulation))

(defn delete-dinosaur [simulation, dinosaur]
  " Deletes dinosaur from simulation "
  (if (instance? Dinosaur dinosaur)
    (private-delete-actor simulation dinosaur)
    simulation))
