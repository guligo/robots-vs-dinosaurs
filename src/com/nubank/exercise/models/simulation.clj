(ns com.nubank.exercise.models.simulation
  "This namespace contains functions that are to be provided to external caller for work with robots versus dinosaurs
  simulation. Majority of functions in this namespace are mutating the internal state of application."
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [clojure.tools.logging :as log]))

(def actors
  "This variable holds simulation state. It represents list of actors."
  (atom []))

(defn simulation
  "This function constructs a map which acts as wrap around list of actors."
  [actors]
  {:actors actors})

(defn simulation-status
  "This function constructs a map which represents update status of simulation."
  [updated]
  {:updated updated})

(defn- update-simulation!
  "This function updates simulation state with provided actor list and checks whether there is any difference between
  previous and new actor states. It returns update status of simulation."
  [updated-actors]
  (let [state-before-update @actors
        state-after-update (reset! actors updated-actors)
        status (simulation-status (not (= state-before-update state-after-update)))]
    (log/debug "Simulation status after last operation" status)
    status))

(defn get-simulation
  "This function returns current simulation."
  []
  (simulation @actors))

(defn delete-simulation!
  "This function empties current simulation.
  It returns update status."
  []
  (locking actors
    (log/debug "Deleting simulation")
    (update-simulation! [])))

(defn create-dinosaur!
  "This function creates dinosaur in current simulation.
  It returns update status."
  [dinosaur]
  (locking actors
    (log/debug "Creating dinosaur" dinosaur)
    (update-simulation! (create-dinosaur @actors dinosaur))))

(defn create-robot!
  "This function creates robot in current simulation.
  It returns update status."
  [robot]
  (locking actors
    (log/debug "Creating robot" robot)
    (update-simulation! (create-robot @actors robot))))

(defn perform-robot-action!
  "This function performs robot action thus updating current simulation.
  It returns update status."
  [robot-id action]
  (locking actors
    (log/debug "Performing action" action "on robot with ID" robot-id)
    (update-simulation! (perform-robot-action @actors robot-id action))))
