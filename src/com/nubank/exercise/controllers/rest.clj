(ns com.nubank.exercise.controllers.rest
  (:require [com.nubank.exercise.models.simulation :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [com.nubank.exercise.views.schemas :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as r]))

(defn request->robot [request]
  (robot (:row request) (:col request) (:dirn request)))

(defn request->dinosaur [request]
  (dinosaur (:row request) (:col request)))

(defn rest-routes []
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
          :body [request Robot]
          (create-robot! (request->robot request))
          (no-content))

    (PATCH "/robots/:id" [id]
           :summary "Updates robot based on provided action"
           :path-params [id :- Long]
           :body [action Action]
           (perform-robot-action! id action)
           (no-content))

    (POST "/dinosaurs" []
          :summary "Creates dinosaur"
          :body [request Dinosaur]
          (create-dinosaur! (request->dinosaur request))
          (no-content))

    (undocumented
      (r/not-found (not-found)))))