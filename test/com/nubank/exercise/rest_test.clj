(ns com.nubank.exercise.rest-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [com.nubank.exercise.rest :refer :all]))

(deftest test-simulation-routes
  (testing "Request to retrieve simulation"
    (let [response (app (mock/request :get "/simulation"))]
      (is (= (:status response) 200))))

  (testing "Request to delete simulation"
    (let [response (app (mock/request :delete "/simulation"))]
      (is (= (:status response) 204)))))

(deftest test-robots-routes
  (testing "Request to create robot"
    (let [response (app (mock/request :post "/robots"))]
      (is (= (:status response) 204))))

  (testing "Request to update robot"
    (let [response (app (mock/request :patch "/robots/1"))]
      (is (= (:status response) 204)))))

(deftest test-dinosaurs-routes
  (testing "Request to create dinosaur"
    (let [response (app (mock/request :post "/dinosaurs"))]
      (is (= (:status response) 204)))))

(deftest test-wrong-routes
  (testing "Request to nonexistent route"
    (let [response (app (mock/request :post "/sharks"))]
      (is (= (:status response) 404))))

  (testing "Request to existing route with wrong method"
    (let [response (app (mock/request :patch "/simulation"))]
      (is (= (:status response) 404)))))
