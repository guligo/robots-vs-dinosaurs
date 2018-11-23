(ns com.nubank.exercise.controllers.rest
  (:require [com.nubank.exercise.models.simulation :refer :all]
            [com.nubank.exercise.views.schemas :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as route]))

(def app
  (api {:swagger {:ui "/docs"
                  :spec "/swagger.json"
                  :data {:info {:title "Robots vs Dinosaurs"
                                :description "REST API for robots versus dinosaurs problem"}
                         :consumes ["application/json"]
                         :produces ["application/json"]}}}

    (GET "/simulation" []
         :summary "Returns current state of simulation"
         (ok (get-simulation)))

    (DELETE "/simulation" []
            :summary "Resets simulation to initial state"
            (delete-simulation!)
            (no-content))

    (POST "/robots" []
          :summary "Creates robot"
          :body [robot RobotSchema]
          (create-robot! robot)
          (no-content))

    (PATCH "/robots/:id" []
           :summary "Updates robot based on provided action"
           :body [action ActionSchema]
           (no-content))

    (POST "/dinosaurs" []
          :summary "Creates dinosaur"
          :body [dinosaur DinosaurSchema]
          (create-dinosaur! dinosaur)
          (no-content))

    (undocumented
      (route/not-found (not-found)))))
