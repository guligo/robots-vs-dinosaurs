(ns com.nubank.exercise.models.simulation
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]))

(def actors (atom []))

(defn simulation [actors]
  {:actors actors})

(defn simulation-status [updated]
  {:updated updated})

(defn- update-simulation! [updated-actors]
  (let [state-before-update @actors
        state-after-update (reset! actors updated-actors)]
    (simulation-status (not (= state-before-update state-after-update)))))

(defn get-simulation []
  "Retrieves ongoing simulation state"
  (simulation @actors))

(defn delete-simulation! []
  "Deletes ongoing simulation"
  (update-simulation! []))

(defn create-dinosaur! [dinosaur]
  "Creates dinosaur in ongoing simulation"
  (update-simulation! (create-dinosaur @actors dinosaur)))

(defn create-robot! [robot]
  "Creates robot in ongoing simulation"
  (update-simulation! (create-robot @actors robot)))

(defn perform-robot-action! [robot-id, action]
  "Performs robot action thus updating ongoing simulation"
  (update-simulation! (perform-robot-action @actors robot-id action)))
