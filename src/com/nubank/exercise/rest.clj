(ns com.nubank.exercise.rest
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.util.response :refer :all]))

(defroutes api-routes
  (GET    "/simulation" []   {:status 200})
  (DELETE "/simulation" []   {:status 204})
  (POST   "/robots"     []   {:status 204})
  (PATCH  "/robots/:id" [id] {:status 204})
  (POST   "/dinosaurs"  []   {:status 204}))

(def app
  (handler/site api-routes))
