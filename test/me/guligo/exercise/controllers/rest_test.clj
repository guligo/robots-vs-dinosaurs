(ns me.guligo.exercise.controllers.rest-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.app :refer :all]
            [me.guligo.exercise.models.robot :refer :all]
            [me.guligo.exercise.models.dinosaur :refer :all]
            [me.guligo.exercise.models.simulation :refer :all]
            [me.guligo.exercise.controllers.rest :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]))

;; Mocking in Midje does not work as expected with let building block. So in order to avoid duplicated code, functions
;; bellow are introduced.

(defn- res-get-simulation []
  (app (mock/request :get "/simulation")))

(defn- res-delete-simulation []
  (app (mock/request :delete "/simulation")))

(defn- res-create-dinosaur [dinosaur]
  (app (-> (mock/request :post "/dinosaurs")
           (mock/content-type "application/json")
           (mock/body (cheshire/generate-string dinosaur)))))

(defn- res-create-robot [robot]
  (app (-> (mock/request :post "/robots")
           (mock/content-type "application/json")
           (mock/body (cheshire/generate-string robot)))))

(defn- res-update-robot [id, action]
  (app (-> (mock/request :patch (str "/robots/" id))
           (mock/content-type "application/json")
           (mock/body (cheshire/generate-string action)))))

(facts "About simulation REST resource"
       (fact "Request to retrieve simulation is handled"
        (:status (res-get-simulation))
        => 200
        (slurp (:body (res-get-simulation)))
        => (cheshire/generate-string {:actors [{:type :robot :row 0 :col 1 :id 0 :dirn :north}
                                               {:type :dinosaur :row 1 :col 0 :id 1}]})
        (provided (get-actors)
                  => [(robot 0 1 :north 0) (dinosaur 1 0 1)] :times 1))
       (fact "Request to delete simulation is handled"
             (:status (res-delete-simulation))
             => 204
             (provided (delete-actors!)
                       => (operation-status true) :times 1)))

(facts "About robots REST resource"
  (fact "Request to create robot can be handled"
        (:status (res-create-robot {:col 1 :row 1 :dirn :north}))
        => 200
        (slurp (:body (res-create-robot {:row 1 :col 1 :dirn :north})))
        => (cheshire/generate-string {:updated true})
        (provided (create-robot! (robot 1 1 :north))
                  => (operation-status true) :times 1))
  (fact "Request with wrong structure to create robot cannot be handled"
        (:status (res-create-robot {:id 1 :row 1 :col 1 :dirn :north}))
        => 400)
  (fact "Empty request to create robot cannot be handled"
        (:status (res-create-robot {}))
        => 400)
  (fact "Request to update robot based on action without parameter can be handled"
        (:status (res-update-robot 1 {:action :attack}))
        => 200
        (slurp (:body (res-update-robot 1 {:action :attack})))
        => (cheshire/generate-string {:updated true})
        (provided (perform-robot-action! 1 (action :attack))
                  => (operation-status true) :times 1))
  (fact "Request to update robot based on action with parameter can be handled"
        (:status (res-update-robot 2 {:action :move :param :forward}))
        => 200
        (slurp (:body (res-update-robot 2 {:action :move :param :forward})))
        => (cheshire/generate-string {:updated true})
        (provided (perform-robot-action! 2 (action :move :forward))
                  => (operation-status true) :times 1))
  (fact "Request to update robot based on wrong action is rejected"
        (:status (res-update-robot 3 {:action :swim}))
        => 400)
  (fact "Request to update robot with wrong arguments is rejected"
        (:status (res-update-robot 4 {:id 4 :action :attack}))
        => 400))

(facts "About dinosaurs REST resource"
  (fact "Request to create dinosaur is handled"
        (:status (res-create-dinosaur {:row 1 :col 1}))
        => 200
        (slurp (:body (res-create-dinosaur {:row 1 :col 1})))
        => (cheshire/generate-string {:updated true})
        (provided (create-dinosaur! (dinosaur 1 1))
                  => (operation-status true) :times 1))
  (fact "Request with wrong structure to create dinosaur cannot be handled"
        (:status (res-create-dinosaur {:pos {:row 1 :col 1}}))
        => 400)
  (fact "Empty request to create dinosaur cannot be handled"
        (:status (res-create-dinosaur {}))
        => 400))

(facts "About wrong routes"
  (fact "Request to nonexistent route is not handled"
    (let [response (app (mock/request :post "/sharks"))]
      (:status response)
      => 404))
  (fact "Request to existing route with wrong method is not handled"
    (let [response (app (mock/request :patch "/simulation"))]
      (:status response)
      => 404)))
