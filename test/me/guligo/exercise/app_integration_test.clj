(ns me.guligo.exercise.app-integration-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.app :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]))

(facts "In general about simulation" :it
       (background (after :facts (app (-> (mock/request :delete "/simulation")
                                          (mock/content-type "application/json")))))

       (fact "It is possible to perform simple operations"

             ;; Create dinosaur and robot
             (app (-> (mock/request :post "/dinosaurs")
                      (mock/content-type "application/json")
                      (mock/body (cheshire/generate-string {:row 24 :col 24}))))
             (app (-> (mock/request :post "/robots")
                      (mock/content-type "application/json")
                      (mock/body (cheshire/generate-string {:row 0 :col 0 :dirn :south}))))

             ;; Request robot to take 23 steps
             (doseq [_ (range 24)]
               (app (-> (mock/request :patch "/robots/1")
                        (mock/content-type "application/json")
                        (mock/body (cheshire/generate-string {:action :move :param :forward})))))

             ;; Request robot to turn left, thus facing the east
             (app (-> (mock/request :patch "/robots/1")
                      (mock/content-type "application/json")
                      (mock/body (cheshire/generate-string {:action :turn :param :left}))))

             ;; Request robot to take 30 steps
             (doseq [_ (range 30)]
               (app (-> (mock/request :patch "/robots/1")
                        (mock/content-type "application/json")
                        (mock/body (cheshire/generate-string {:action :move :param :forward})))))

             ;; Out of 30 robot's steps only 23 are possible, since there is dinosaur in front it
             (let [response (app (-> (mock/request :get "/simulation")
                                    (mock/content-type "application/json")))]
               (:actors (cheshire/parse-string (slurp (:body response)) true)) => [{:id 0 :type "dinosaur" :row 24 :col 24}
                                                                                   {:id 1 :type "robot" :row 24 :col 23 :dirn "east"}]))

       (fact "It is possible to perform complex operations"

             ;; Create robots and dinosaurs in check pattern
             (doseq [row (range -5 55)]
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
               (count (:actors (cheshire/parse-string (slurp (:body response)) true))) => 2500)

             ;; Request all of them to attack, only robots however would perform the action
             (doseq [id (range 0 2500)]
               (app (-> (mock/request :patch (str "/robots/" id))
                        (mock/content-type "application/json")
                        (mock/body (cheshire/generate-string {:action :attack})))))
             (let [response (app (-> (mock/request :get "/simulation")
                                     (mock/content-type "application/json")))]
               (count (:actors (cheshire/parse-string (slurp (:body response)) true))) => 1250))

       (fact "It is possible to perform complex operations in parallel"

             ;; Create robots and dinosaurs in check pattern sending requests from different threads
             (doseq [row (range -5 55)]
               (doseq [col (range -5 55)]
                 (.start
                   (Thread.
                     (fn []
                       (if (odd? col)
                         (app (-> (mock/request :post "/dinosaurs")
                                  (mock/content-type "application/json")
                                  (mock/body (cheshire/generate-string {:row row :col col}))))
                         (app (-> (mock/request :post "/robots")
                                  (mock/content-type "application/json")
                                  (mock/body (cheshire/generate-string {:row row :col col :dirn :north}))))))))))
             (Thread/sleep 5000)
             (let [response (app (-> (mock/request :get "/simulation")
                                     (mock/content-type "application/json")))]
               (count (:actors (cheshire/parse-string (slurp (:body response)) true))) => 2500)

             ;; Request all of them to attack from different threads, only robots however would perform the action
             (doseq [id (range 0 2500)]
               (.start
                 (Thread.
                   (fn []
                     (app (-> (mock/request :patch (str "/robots/" id))
                              (mock/content-type "application/json")
                              (mock/body (cheshire/generate-string {:action :attack}))))))))
             (Thread/sleep 5000)
             (let [response (app (-> (mock/request :get "/simulation")
                                     (mock/content-type "application/json")))]
               (count (:actors (cheshire/parse-string (slurp (:body response)) true))) => 1250)))
