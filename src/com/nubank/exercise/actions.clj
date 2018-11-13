(ns com.nubank.exercise.actions
  (:require [com.nubank.exercise.core :refer :all])
  (:import (com.nubank.exercise.core Cell Dinosaur)))

(defn attack[simulation, robot]
  "Destroys dinosaurs around robot"
  (delete-dinosaur
    (delete-dinosaur
      (delete-dinosaur
        (delete-dinosaur simulation, (Dinosaur. (Cell. (- (:row (:pos robot)) 1) (:col (:pos robot)))))
        (Dinosaur. (Cell. (+ (:row (:pos robot)) 1) (:col (:pos robot)))))
      (Dinosaur. (Cell. (:row (:pos robot)) (- (:col (:pos robot)) 1))))
    (Dinosaur. (Cell. (:row (:pos robot)) (+ (:col (:pos robot)) 1)))))
