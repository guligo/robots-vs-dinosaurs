(ns me.guligo.exercise.models.dinosaur-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.models.core :refer :all]
            [me.guligo.exercise.models.dinosaur :refer :all]))

(facts "About dinosaur"
       (fact "Creation of dinosaur actually means creation of an actor of 'dinosaur' type"
             (create-dinosaur [] (dinosaur 22 23))
             => [(actor :dinosaur 22 23 0)])
       (fact "Deletion of dinosaur actually means deletion of an actor of 'dinosaur' type"
             (delete-dinosaur [(dinosaur 24 25 3)] 24 25)
             => [])
       (fact "Deletion of dinosaur will not remove actors of other type"
             (delete-dinosaur [(actor :other 48 49 1000)] 48 49)
             => [(actor :other 48 49 1000)]))
