(ns me.guligo.exercise.views.schemas
  "This namespace contains schemas used by Compojure API for JSON validation."
  (:require [me.guligo.exercise.models.common :refer :all]
            [schema.core :as s]))

(s/defschema Dinosaur
             "This schema is used for validation of dinosaur request object."
             {:row (s/constrained s/Int #(<= min-boundary % max-boundary))
              :col (s/constrained s/Int #(<= min-boundary % max-boundary))})

(s/defschema Robot
             "This schema is used for validation of robot request object."
             {:row (s/constrained s/Int #(<= min-boundary % max-boundary))
              :col (s/constrained s/Int #(<= min-boundary % max-boundary))
              :dirn (s/enum :north
                            :west
                            :south
                            :east)})

;; Unfortunately Compojure API does not seem to support s/if construct of schema, hence schema itself is not exactly
;; precise and certain aspects of validation are provided by other namespaces and functions.

(s/defschema Action
             "This schema is used for validation of action request object."
             {:action (s/enum :move
                              :turn
                              :attack)
              (s/optional-key :param) (s/enum :forward
                                              :backward
                                              :left
                                              :right)})
