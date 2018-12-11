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

(defn simulation
  "This function constructs a map which acts as wrap around list of actors."
  [actors]
  {:actors actors})

(defn operation-status
  "This function constructs a map which represents update status of simulation."
  ([updated] {:updated updated})
  ([prev-simulation-state new-simulation-state] (operation-status (not (= prev-simulation-state new-simulation-state)))));

(defn rest-routes
  "This function creates simulation REST API routes as well as configures Swagger for documenting those APIs."
  []
  (api {:swagger {:ui "/docs"
                  :spec "/swagger.json"
                  :data {:info {:title "Robots vs Dinosaurs"
                                :description "REST API for robots versus dinosaurs simulation"}
                         :consumes ["application/json"]
                         :produces ["application/json"]}}}
    (GET "/simulation" []
      :summary "Returns current simulation"
      (ok (simulation (get-actors))))

    (DELETE "/simulation" []
            :summary "Resets simulation"
            (delete-actors!)
            (no-content))

    (POST "/robots" []
          :summary "Creates robot"
          :body [request Robot]
          (ok (operation-status
                (get-actors)
                (create-robot! (request->robot request)))))

    (PATCH "/robots/:id" [id]
           :summary "Updates robot based on provided action"
           :path-params [id :- Long]
           :body [action Action]
           (ok (operation-status
                 (get-actors)
                 (perform-robot-action! id (request->action action)))))

    (POST "/dinosaurs" []
          :summary "Creates dinosaur"
          :body [request Dinosaur]
          (ok (operation-status
                (get-actors)
                (create-dinosaur! (request->dinosaur request)))))

    (undocumented
      (r/not-found (not-found)))))
