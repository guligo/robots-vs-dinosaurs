(ns com.nubank.exercise.controllers.rest
  (:require [com.nubank.exercise.models.core :refer :all]
            [com.nubank.exercise.views.data :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as route])
  (:import [com.nubank.exercise.views.data Simulation]))

(def simulation (atom (Simulation. [])))

(def app
  (api {:swagger {:ui "/docs"
                  :spec "/swagger.json"
                  :data {:info {:title "Robots vs Dinosaurs"
                                :description "REST API for robots versus dinosaurs problem"}
                         :consumes ["application/json"]
                         :produces ["application/json"]}}}

    (GET "/simulation" []
         :summary "Returns current state of simulation"
         (ok @simulation))

    (DELETE "/simulation" []
            :summary "Resets simulation to initial state"
            (reset! simulation (Simulation. []))
            (no-content))

    (POST "/robots" []
          :summary "Creates robot"
          :body [robot RobotSchema]
          (reset! simulation (create-robot @simulation robot))
          (no-content))

    (PATCH "/robots/:id" []
           :summary "Updates robot based on provided action"
           :body [action ActionSchema]
           (no-content))

    (POST "/dinosaurs" []
          :summary "Creates dinosaur"
          :body [dinosaur DinosaurSchema]
          (reset! simulation (create-dinosaur @simulation dinosaur))
          (no-content))

    (undocumented
      (route/not-found (not-found)))))
