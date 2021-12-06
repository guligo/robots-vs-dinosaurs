(ns me.guligo.exercise.views.schemas-test
  (:require [midje.sweet :refer :all]
            [me.guligo.exercise.views.schemas :refer :all]
            [schema.core :as s]))

(facts "About dinosaur schema"
       (fact "Location row and column must be provided"
             (s/validate Dinosaur {:row 0 :col 1}) => {:row 0 :col 1}
             (s/validate Dinosaur {:col 1}) => (throws Exception)
             (s/validate Dinosaur {:row 0}) => (throws Exception)
             (s/validate Dinosaur {}) => (throws Exception))
       (fact "Impossible to provide any other key apart from row and column"
             (s/validate Dinosaur {:row 2 :col 3 :id 0}) => (throws Exception))
       (fact "Impossible to provide negative values into row and column"
             (s/validate Dinosaur {:row 20 :col -10}) => (throws Exception))
       (fact "Impossible to provide string values into row and column"
             (s/validate Dinosaur {:row "10" :col 20}) => (throws Exception))
       (fact "Impossible to provide floating values into row and column"
             (s/validate Dinosaur {:row 1.5 :col 15}) => (throws Exception))
       (fact "Impossible to provide row and column outside of boundaries"
             (s/validate Dinosaur {:row -1 :col 1}) => (throws Exception)
             (s/validate Dinosaur {:row 1 :col 50}) => (throws Exception)))

(facts "About robot schema"
       (fact "Location row, column and direction must be provided"
             (s/validate Robot {:row 0 :col 49 :dirn :north}) => {:row 0 :col 49 :dirn :north}
             (s/validate Robot {:row 0 :col 49}) => (throws Exception)
             (s/validate Robot {:row 0 :dirn :south}) => (throws Exception)
             (s/validate Robot {}) => (throws Exception))
       (fact "Direction can only have limited set of values"
             (s/validate Robot {:row 0 :col 0 :dirn :north}) => {:row 0 :col 0 :dirn :north}
             (s/validate Robot {:row 0 :col 0 :dirn :east}) => {:row 0 :col 0 :dirn :east}
             (s/validate Robot {:row 0 :col 0 :dirn :south}) => {:row 0 :col 0 :dirn :south}
             (s/validate Robot {:row 0 :col 0 :dirn :west}) => {:row 0 :col 0 :dirn :west}
             (s/validate Robot {:row 0 :col 0 :dirn :forward}) => (throws Exception)
             (s/validate Robot {:row 0 :col 0 :dirn nil}) => (throws Exception)))

(facts "About action schema"
       (fact "Action name must be provided"
             (s/validate Action {:action :attack}) => {:action :attack}
             (s/validate Action {}) => (throws Exception))
       (fact "Action name can have limited set of values"
             (s/validate Action {:action :move :param :forward}) => {:action :move :param :forward}
             (s/validate Action {:action :turn :param :left}) => {:action :turn :param :left}
             (s/validate Action {:action :attack}) => {:action :attack}
             (s/validate Action {:action :sleep :param :pillow}) => (throws Exception))
       (fact "Attacking action does not accept any parameter"
             (s/validate Action {:action :attack}) => {:action :attack}
             (s/validate Action {:action :attack :param :laser}) => (throws Exception))
       (fact "Moving action accepts only limited set of parameters"
             (s/validate Action {:action :move :param :forward}) => {:action :move :param :forward}
             (s/validate Action {:action :move :param :backward}) => {:action :move :param :backward})
       ; (s/validate ActionSchema {:action :move :param :left}) => (throws Exception)
       ; (s/validate ActionSchema {:action :move}) => (throws Exception)
       (fact "Turning action accepts only limited set of parameters"
             (s/validate Action {:action :turn :param :left}) => {:action :turn :param :left}
             (s/validate Action {:action :turn :param :right}) => {:action :turn :param :right}
             (s/validate Action {:action :turn :param :up}) => (throws Exception)))
             ; (s/validate ActionSchema {:action :turn}) => (throws Exception)
