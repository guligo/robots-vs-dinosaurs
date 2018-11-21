(ns com.nubank.exercise.models.common-test
  (:require [midje.sweet :refer :all]
            [com.nubank.exercise.models.common :refer :all]))

(facts "About actor creation"
       (fact "Possible to create actor in an empty list"
             (create-actor [] (actor 0 0)) => [(actor 0 0)])
       (fact "Possible to create actor in an non-empty list"
             (create-actor [(actor 0 0)] (actor 1 1)) => [(actor 0 0) (actor 1 1)])
       (fact "Impossible to create actor if actor with such coordinates already exist"
             (create-actor [(actor 1 0) (actor 0 1)] (actor 0 1)) => [(actor 1 0) (actor 0 1)])
       (fact "Impossible to create actor outside of minimal boundaries"
             (create-actor [] (actor -1 0)) => [])
       (fact "Impossible to create actor outside of maximal boundaries"
             (create-actor [] (actor 0 50)) => []))

(facts "About actor deletion"
       (fact "Possible to delete an actor from existing location"
             (delete-actor [(actor 49 49)] 49 49) => [])
       (fact "Impossible to delete an actor from empty list"
             (delete-actor [] 49 48) => [])
       (fact "Impossible to delete an actor from nonexistent location"
             (delete-actor [(actor 24 25) (actor 25 24)] 49 49) => [(actor 24 25) (actor 25 24)]))
