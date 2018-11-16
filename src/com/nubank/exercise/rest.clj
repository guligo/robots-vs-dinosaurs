(ns com.nubank.exercise.rest
  (:require [com.nubank.exercise.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]))

(def simulation (atom (->Simulation [])))

(defroutes api-routes
  (GET    "/simulation" [] {:status 200, :body @simulation})
  (DELETE "/simulation" [] (reset! simulation (->Simulation [])) {:status 204})
  (POST   "/robots"     {robot :body} (reset! simulation (create-robot @simulation robot)) {:status 204})
  (PATCH  "/robots/:id" [id] {:status 204})
  (POST   "/dinosaurs"  {dinosaur :body} (reset! simulation (create-dinosaur @simulation dinosaur)) {:status 204})
  (route/not-found      {:status 404}))

(def app
  (->
    (handler/site api-routes)
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))
