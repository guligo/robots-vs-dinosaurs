(ns com.nubank.exercise.models.core-test
  (:require [midje.sweet :refer :all]
            [com.nubank.exercise.models.core :refer :all]))

(facts "About actor creation"
       (fact "Possible to create an actor in an empty list"
             (create-actor [] (actor :actor1 0 0))
             => [(actor :actor1 0 0 0)])
       (fact "Possible to create an actor in an non-empty list"
             (create-actor [(actor :actor1 0 0 1)] (actor :actor2 1 1))
             => [(actor :actor1 0 0 1) (actor :actor2 1 1 2)])
       (fact "Impossible to create an actor if actor with such coordinates already exist"
             (create-actor [(actor :actor1 0 1 2) (actor :actor2 1 0 3)] (actor :actor1 0 1))
             => [(actor :actor1 0 1 2) (actor :actor2 1 0 3)])
       (fact "Impossible to create an actor outside of minimal boundaries"
             (create-actor [] (actor :actor1 -1 0))
             => [])
       (fact "Impossible to create an actor outside of maximal boundaries"
             (create-actor [] (actor :actor2 0 50))
             => [])
       (fact "Impossible to create an actor without required fields"
             (create-actor [] {:row 0 :col 1})
             => (throws AssertionError)
             (create-actor [] {:type :actor1})
             => (throws AssertionError)))

(facts "About actor retrieval"
       (fact "Possible to retrieve actor by ID knowing its type"
             (get-actor [(actor :actor1 0 1 2)] :actor1 2)
             => (actor :actor1 0 1 2)
             (get-actor [(actor :actor1 0 1 2)] :actor2 2)
             => nil
             (get-actor [(actor :actor1 0 1 2)] :actor1 0)
             => nil))

(facts "About actor update"
       (fact "Possible to update actor's location"
             (update-actor [(actor :actor1 30 40 1000)] (actor :actor1 10 20 1000))
             => [(actor :actor1 10 20 1000)])
       (fact "Impossible to update actor's location if some other actor is already in that spot"
             (update-actor [(actor :actor1 35 45 0) (actor :actor2 30 40 1)] (actor :actor2 35 45 1))
             => [(actor :actor1 35 45 0) (actor :actor2 30 40 1)])
       (fact "Impossible to update actor's location to invalid one"
             (update-actor [(actor :actor1 5 10 10000)] (actor :actor1 -1 1 10000))
             => [(actor :actor1 5 10 10000)])
       (fact "Impossible to update actor's type"
             (update-actor [(actor :actor2 10 20 100000)] (actor :actor2 10 20 100000))
             => [(actor :actor2 10 20 100000)]))

(facts "About actor deletion"
       (fact "Possible to delete an actor from existing location"
             (delete-actor [(actor :actor1 49 49 100)] :actor1 49 49)
             => [])
       (fact "Impossible to delete an actor from empty list"
             (delete-actor [] :actor1 49 48)
             => [])
       (fact "Impossible to delete an actor from nonexistent location"
             (delete-actor [(actor :actor1 24 25 101) (actor :actor1 25 24 102)] :actor1 49 49)
             => [(actor :actor1 24 25 101) (actor :actor1 25 24 102)])
       (fact "Impossible to delete an actor from existing location when wrong actor type is provided"
             (delete-actor [(actor :actor1 1 2 103) (actor :actor2 3 4 104)] :actor1 3 4)
             => [(actor :actor1 1 2 103) (actor :actor2 3 4 104)]))

(facts "About actor ID"
       (fact "ID of an actor should increase with every next added actor"
             (-> []
                 (create-actor (actor :actor1 0 0))
                 (create-actor (actor :actor2 49 49))
                 (create-actor (actor :actor3 24 24)))
             => [(actor :actor1 0 0 0) (actor :actor2 49 49 1) (actor :actor3 24 24 2)])
       (fact "ID of an actor should be next after maximal ID in list of actors"
             (-> []
                 (create-actor (actor :actor1 0 49))
                 (delete-actor :actor1 0 49)
                 (create-actor (actor :actor2 49 0))
                 (delete-actor :actor2 49 0)
                 (create-actor (actor :actor3 24 24)))
             => [(actor :actor3 24 24 0)])
       (fact "Actors will not be added to simulation if maximum ID in simulation has reached one less than maximum long value"
             (create-actor [(actor :actor1 0 49 (dec Long/MAX_VALUE))] (actor :actor2 49 0))
             => [(actor :actor1 0 49 (dec Long/MAX_VALUE))]))
