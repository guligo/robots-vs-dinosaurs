(ns com.nubank.exercise.rest-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [com.nubank.exercise.rest :refer :all]))

(facts "About simulation REST resource"
  (fact "Request to retrieve simulation can be handled"
    (let [response (app (mock/request :get "/simulation"))]
      (:status response) => 200))

  (fact "Request to delete simulation can be handled"
    (let [response (app (mock/request :delete "/simulation"))]
      (:status response) => 204)))

(facts "About robots REST resource"
  (fact "Request to create robot can be handled"
    (let [response (app (mock/request :post "/robots"))]
      (:status response) => 204))

  (fact "Request to update robot can be handled"
    (let [response (app (mock/request :patch "/robots/1"))]
      (:status response) => 204)))

(facts "About dinosaurs REST resource"
  (testing "Request to create dinosaur can be handled"
    (let [response (app (mock/request :post "/dinosaurs"))]
      (:status response) => 204)))

(facts "About wrong routes"
  (fact "Request to nonexistent route is not handled"
    (let [response (app (mock/request :post "/sharks"))]
      (:status response) => 404))

  (testing "Request to existing route with wrong method is not handled"
    (let [response (app (mock/request :patch "/simulation"))]
      (:status response) => 404)))
