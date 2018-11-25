(ns com.nubank.exercise.views.schemas
  "This namespace contains schemas used by Compojure API for JSON validation."
  (:require [com.nubank.exercise.models.common :refer :all]
            [schema.core :as s]))

(s/defschema Dinosaur
             "This schema is used for validation of dinosaur request object."
             {:row (s/constrained s/Int #(<= min-boundary % max-boundary))
              :col (s/constrained s/Int #(<= min-boundary % max-boundary))})

(s/defschema Robot
             "This schema is used for validation of robot request object."
             {:row (s/constrained s/Int #(<= 0 % 49))
              :col (s/constrained s/Int #(<= 0 % 49))
              :dirn (s/enum :north
                            :west
                            :south
                            :east)})

;; Unfortunately Compojure API does not seem to support s/if construct of schema, hence schema itself is not exactly
;; precise and certain aspects of validation are provided by other components.

(s/defschema Action
             "This schema is used for validation of action request object."
             {:action (s/enum :move
                              :turn
                              :attack)
              (s/optional-key :param) (s/enum :forward
                                              :backward
                                              :left
                                              :right)})
