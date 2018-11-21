(ns com.nubank.exercise.controllers.rest-test
  (:require [clojure.test :refer :all]
            [midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [com.nubank.exercise.controllers.rest :refer :all]
            [cheshire.core :as cheshire]))

(facts "About simulation REST resource"
  (fact "Request to retrieve simulation can be handled"
    (let [response (app (mock/request :get "/simulation"))]
      (:status response) => 200))
  (fact "Request to delete simulation can be handled"
    (let [response (app (mock/request :delete "/simulation"))]
      (:status response) => 204)))

(facts "About robots REST resource"
  (fact "Request to create robot can be handled"
        (let [robot {:id 1 :row 1 :col 1 :dirn :north}
              response (app (-> (mock/request :post "/robots")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string robot))))]
          (:status response) => 204))
  (fact "Request with wrong structure to create robot cannot be handled"
        (let [robot {:id 1 :pos {:row 1 :col 1}}
              response (app (-> (mock/request :post "/robots")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string robot))))]
          (:status response) => 400))
  (fact "Empty request to create robot cannot be handled"
        (let [robot {}
              response (app (-> (mock/request :post "/robots")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string robot))))]
          (:status response) => 400))
  (fact "Request to update robot based on action without parameter can be handled"
        (let [action {:action :attack}
              response (app (-> (mock/request :patch "/robots/1")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string action))))]
          (:status response) => 204))
  (fact "Request to update robot based on action with parameter can be handled"
        (let [action {:action :move :param :forward}
              response (app (-> (mock/request :patch "/robots/2")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string action))))]
          (:status response) => 204)))

(facts "About dinosaurs REST resource"
  (fact "Request to create dinosaur can be handled"
        (let [dinosaur {:row 1 :col 1}
              response (app (-> (mock/request :post "/dinosaurs")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string dinosaur))))]
          (:status response) => 204))
  (fact "Request with wrong structure to create dinosaur cannot be handled"
        (let [dinosaur {:pos {:row 1 :col 1}}
              response (app (-> (mock/request :post "/dinosaurs")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string dinosaur))))]
          (:status response) => 400))
  (fact "Empty request to create dinosaur cannot be handled"
        (let [dinosaur {}
              response (app (-> (mock/request :post "/dinosaurs")
                                (mock/content-type "application/json")
                                (mock/body (cheshire/generate-string dinosaur))))]
          (:status response) => 400)))

(facts "About wrong routes"
  (fact "Request to nonexistent route is not handled"
    (let [response (app (mock/request :post "/sharks"))]
      (:status response) => 404))
  (fact "Request to existing route with wrong method is not handled"
    (let [response (app (mock/request :patch "/simulation"))]
      (:status response) => 404)))
