(ns com.nubank.exercise.models.core
  "This namespace is responsible for core functionality of simulation. Instead of working with specific entities
  like robot or dinosaur, it uses abstract entity called actor. Actors must have type assigned which is exactly how
  it is turned from abstract into specific."
  (:require [com.nubank.exercise.models.common :refer :all]))

(defn actor
  "This function constructs map which represents actor entity."
  ([type row col] {:type type :row row :col col})
  ([type row col id] {:type type :row row :col col :id id}))

(defn- actors-in-same-location?
  "This function checks whether two actors are in same location."
  [actor1 actor2]
  (and (not (= (:id actor1) (:id actor2)))
       (= (:row actor1) (:row actor2))
       (= (:col actor1) (:col actor2))))

(defn- actors-equal-based-on-location-and-type?
  "This function checks whether two actors are same based on type and location."
  [actor1 actor2]
  (and (= (:type actor1) (:type actor2))
       (actors-in-same-location? actor1 actor2)))

(defn- no-other-actors-with-same-id?
  "This function check whether there is another actor among others with same ID."
  [actors actor]
  (not-any? #(= (:id actor) (:id %)) actors))

(defn- no-other-actors-with-same-location?
  "This function check whether there is another actor among others with same location."
  [actors actor]
  (not-any? #(actors-in-same-location? actor %) actors))

(defn- get-next-id
  "This function returns next ID for an actor."
  [actors]
  (if (not (empty? actors))
    (inc (reduce max (map :id actors))) 0))

(defn- actor-within-boundaries?
  "This function checks whether actor's location is within simulation boundaries."
  [actor]
  (and (>= (:col actor) min-boundary)
       (<= (:col actor) max-boundary)
       (>= (:row actor) min-boundary)
       (<= (:row actor) max-boundary)))

(defn create-actor
  "This function adds an actor to actor list.
  It returns updated list of actors."
  [actors actor]
  {:pre [(contains? actor :type)
         (contains? actor :row)
         (contains? actor :col)]}
  (let [next-id (get-next-id actors)]
  (if (and (actor-within-boundaries? actor)
           (no-other-actors-with-same-location? actors actor)
           (no-other-actors-with-same-id? actors actor)
           (< next-id Long/MAX_VALUE))
    (conj actors (assoc actor :id next-id)) actors)))

(defn get-actor
  "This function retrieves actor by ID assuming caller knows its type."
  [actors type actor-id]
  (some #(if (and (= type (:type %)) (= actor-id (:id %))) %) actors))

(defn update-actor
  "This function updates actor within simulation based on its ID.
  It returns updated list of actors."
  [actors actor]
  (let [index (first (some
                       #(when (and (= (:id (second %)) (:id actor))
                                   (= (:type (second %) (:type actor)))) %)
                       (map-indexed vector actors)))]
    (if (and (actor-within-boundaries? actor)
             (no-other-actors-with-same-location? actors actor)
             (some? index))
      (assoc actors index actor)
      actors)))

(defn delete-actor
  "This function deletes an actor from list of actors based on its location assuming caller knows its type.
  It returns updated list of actors."
  [actors type row col]
  (into [] (remove #(actors-equal-based-on-location-and-type? (actor type row col) %) actors)))
