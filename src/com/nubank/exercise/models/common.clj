(ns com.nubank.exercise.models.common)

(def min-boundary 0)

(def max-boundary 49)

(defrecord Actor [row col])

(defn actor [row, col]
  "Constructs actor map"
  (->Actor row col))

(defn- is-actors-location [actor, row, col]
  "Checks whether actors location is represented by provided row and column"
  (and (= (:row actor) row) (= (:col actor) col)))

(defn- are-actors-equal [actor1, actor2]
  "Checks whether two actors are same based on location"
  (is-actors-location actor1 (:row actor2) (:col actor2)))

(defn- is-actor-within-boundaries [actor]
  "Checks whether actor's location is within simulation boundaries"
  (and (>= (:col actor) min-boundary)
       (<= (:col actor) max-boundary)
       (>= (:row actor) min-boundary)
       (<= (:row actor) max-boundary)))

(defn create-actor [actors, actor]
  "Adds an actor to actor list if one with such location does not already exist"
  (if (and (is-actor-within-boundaries actor)
           (not-any? #(are-actors-equal actor %) actors))
    (conj actors actor)
    actors))

(defn delete-actor [actors, row, col]
  "Deletes an actor from list of actors based on its location"
  (remove #(is-actors-location % row col) actors))
