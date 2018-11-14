(ns com.nubank.exercise.rest
  (:require [com.nubank.exercise.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware])
  (:import (com.nubank.exercise.core Simulation)))

(defroutes api-routes
  (GET    "/simulation" []   {:status 200, :body (Simulation.[])})
  (DELETE "/simulation" []   {:status 204})
  (POST   "/robots"     []   {:status 204})
  (PATCH  "/robots/:id" [id] {:status 204})
  (POST   "/dinosaurs"  []   {:status 204})
  (route/not-found           {:status 404}))

(def app
  (->
    (handler/site api-routes)
    (middleware/wrap-json-response)))
