(ns com.nubank.exercise.models.core
  (:require [com.nubank.exercise.models.common :refer :all]))

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

(defn- no-other-actors-with-same-id? [actors, actor]
  (not-any? #(= (:id actor) (:id %)) actors))

(defn- no-other-actors-with-same-location? [actors, actor]
  (not-any? #(actors-in-same-location? actor %) actors))

(defn- get-next-id [actors]
  (if (not (empty? actors))
    (inc (reduce max (map #(:id %) actors))) 0))

(defn- actor-within-boundaries? [actor]
  "Checks whether actor's location is within simulation boundaries"
  (and (>= (:col actor) min-boundary)
       (<= (:col actor) max-boundary)
       (>= (:row actor) min-boundary)
       (<= (:row actor) max-boundary)))

(defn create-actor [actors, actor]
  {:pre [(contains? actor :type)
         (contains? actor :row)
         (contains? actor :col)]}
  "Adds an actor to actor list if one with such location does not already exist"
  (let [next-id (get-next-id actors)]
  (if (and (actor-within-boundaries? actor)
           (no-other-actors-with-same-location? actors actor)
           (no-other-actors-with-same-id? actors actor)
           (< next-id Long/MAX_VALUE))
    (conj actors (assoc actor :id next-id)) actors)))

(defn get-actor [actors, type, actor-id]
  "Retrieves actor by its ID"
  (some #(if (and (= type (:type %)) (= actor-id (:id %))) %) actors))

(defn update-actor [actors, actor]
  "Updates actor within simulation based on its ID"
  (let [index (first (map first
                          (filter
                            #(and (= (:id (second %)) (:id actor))
                                  (= (:type (second %) (:type actor))))
                            (map-indexed vector actors))))]
    (if (and (actor-within-boundaries? actor)
             (no-other-actors-with-same-location? actors actor)
             (some? index))
      (assoc actors index actor)
      actors)))

(defn delete-actor [actors, type, row, col]
  "Deletes an actor from list of actors based on its location"
  (remove #(actors-equal-based-on-location-and-type? (actor type row col) %) actors))
