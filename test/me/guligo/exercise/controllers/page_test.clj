(ns me.guligo.exercise.controllers.page-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.app :refer :all]
            [ring.mock.request :as mock]))

(facts "About page controller"
       (fact "Loads dashboard page"
             (let [response (app (mock/request :get "/dashboard"))]
               (:status response) => 200
               (.contains (:body response) "<h1>Simulation</h1>") => true)))
