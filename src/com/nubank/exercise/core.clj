(ns com.nubank.exercise.core)

(defrecord Cell [row, col])

(defrecord Robot [id, pos, dirn])

(defrecord Dinosaur [pos])

(defrecord Simulation [actors])

(defn- private-create-actor [simulation, actor]
  "Adds an actor to simulation"
  (Simulation. (conj (:actors simulation) actor)))

(defn- private-delete-actor [simulation, target-actor]
  "Deletes an actor from simulation"
  (Simulation. (remove (fn [actor] (= target-actor actor)) (:actors simulation))))

(defn create-robot [simulation, robot]
  "Adds robot to simulation"
  (private-create-actor simulation robot))

(defn create-dinosaur [simulation, dinosaur]
  "Adds dinosaur to simulation"
  (private-create-actor simulation dinosaur))

(defn delete-dinosaur [simulation, dinosaur]
  "Deletes dinosaur from simulation"
  (if (instance? Dinosaur dinosaur)
    (private-delete-actor simulation dinosaur)
    simulation))
