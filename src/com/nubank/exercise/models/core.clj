(ns com.nubank.exercise.models.core)

(def min-boundary 0)

(def max-boundary 49)

(defn actor
  "Constructs actor map"
  ([type, row, col] {:type type :row row :col col})
  ([type, row, col, id] {:type type :row row :col col :id id}))

(defn- actors-in-same-location? [actor1, actor2]
  "Checks whether two actors are in same location"
  (and (not (= (:id actor1) (:id actor2)))
       (= (:row actor1) (:row actor2)) (= (:col actor1) (:col actor2))))

(defn- actors-equal-based-on-location-and-type? [actor1, actor2]
  "Checks whether two actors are same based on type and location"
  (and (= (:type actor1) (:type actor2))
       (actors-in-same-location? actor1 actor2)))

(defn- actors-equals-based-on-id? [actor1, actor2]
  (= (:id actor1) (:id actor2)))

(defn- get-id [actors]
  (if (not (empty? actors))
    (inc (reduce max (map #(:id %) actors)))
    0))

(defn- actor-within-boundaries? [actor]
  "Checks whether actor's location is within simulation boundaries"
  (and (>= (:col actor) min-boundary)
       (<= (:col actor) max-boundary)
       (>= (:row actor) min-boundary)
       (<= (:row actor) max-boundary)))

(defn create-actor [actors, actor]
  "Adds an actor to actor list if one with such location does not already exist"
  (if (and (actor-within-boundaries? actor)
           (not-any? #(actors-in-same-location? actor %) actors)
           (not-any? #(actors-equals-based-on-id? actor %) actors))
    (conj actors (assoc actor :id (get-id actors)))
    actors))

(defn update-actor [actors, actor]
  "Updates actor within simulation based on its ID"
  (let [index (first (map first
                          (filter
                            #(and (= (:id (second %)) (:id actor))
                                  (= (:type (second %) (:type actor))))
                            (map-indexed vector actors))))]
    (if (and (actor-within-boundaries? actor)
             (some? index)
             (not-any? #(actors-in-same-location? actor %) actors))
      (assoc actors index actor)
      actors)))

(defn delete-actor [actors, type, row, col]
  "Deletes an actor from list of actors based on its location"
  (remove #(actors-equal-based-on-location-and-type? (actor type row col) %) actors))
