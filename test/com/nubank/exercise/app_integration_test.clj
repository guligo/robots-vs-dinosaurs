(ns com.nubank.exercise.app-integration-test
  (:require [midje.sweet :refer :all]
            [com.nubank.exercise.app :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]))

(facts "About application"
       (fact "Not more than 2500 robots and dinosaurs can be created"
             (do (doseq [row (range -5 55)]
                   (doseq [col (range -5 55)]
                     (if (odd? col)
                       (app (-> (mock/request :post "/dinosaurs")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string {:row row :col col}))))
                       (app (-> (mock/request :post "/robots")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string {:row row :col col :dirn :north})))))))
                 (let [response (app (-> (mock/request :get "/simulation")
                                         (mock/content-type "application/json")))]
                   (count (:actors (cheshire/parse-string (slurp (:body response)) true))) => 2500))))
