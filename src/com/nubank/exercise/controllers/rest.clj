(ns com.nubank.exercise.controllers.rest
  "This namespace contains functions used for creating REST API routes."
  (:require [com.nubank.exercise.models.simulation :refer :all]
            [com.nubank.exercise.models.robot :refer :all]
            [com.nubank.exercise.models.dinosaur :refer :all]
            [com.nubank.exercise.views.schemas :refer :all]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [compojure.route :as r]))

(defn- request->robot
  "This function converts robot request object into robot actor."
  [request]
  (robot (:row request) (:col request) (:dirn request)))

(defn- request->dinosaur
  "This function converts dinosaur request object into dinosaur actor."
  [request]
  (dinosaur (:row request) (:col request)))

(defn- request->action
  "This function converts action request object into action structure."
  [request]
  (if (contains? request :param)
    (action (:action request) (:param request))
    (action (:action request))))

(defn rest-routes
  "This function creates simulation REST API routes as well as configures Swagger for documenting those APIs."
  []
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
          (ok (create-robot! (request->robot request))))

    (PATCH "/robots/:id" [id]
           :summary "Updates robot based on provided action"
           :path-params [id :- Long]
           :body [action Action]
           (ok (perform-robot-action! id (request->action action))))

    (POST "/dinosaurs" []
          :summary "Creates dinosaur"
          :body [request Dinosaur]
          (ok (create-dinosaur! (request->dinosaur request))))

    (undocumented
      (r/not-found (not-found)))))
