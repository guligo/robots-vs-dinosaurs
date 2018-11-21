(ns com.nubank.exercise.models.common)

(def min-boundary 0)

(def max-boundary 49)

(defrecord Actor [row col])

(defn actor [row, col]
  (->Actor row col))

(defn- is-actors-location [actor, row, col]
  (and (= (:row actor) row) (= (:col actor) col)))

(defn- are-actors-equal [actor1, actor2]
  (is-actors-location actor1 (:row actor2) (:col actor2)))

(defn- is-actor-within-boundaries [actor]
  (and (>= (:col actor) min-boundary)
       (<= (:col actor) max-boundary)
       (>= (:row actor) min-boundary)
       (<= (:row actor) max-boundary)))

(defn create-actor [actors, actor]
  "Adds an actor to list of actors if actors with such coordinates does not already exist"
  (if (and (is-actor-within-boundaries actor)
           (not-any? #(are-actors-equal actor %) actors))
    (conj actors actor)
    actors))

(defn delete-actor [actors, row, col]
  "Deletes an actor from list of actors based on its coordinates"
  (remove #(is-actors-location % row col) actors))
