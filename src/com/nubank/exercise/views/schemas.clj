(ns com.nubank.exercise.views.schemas
  (:require [com.nubank.exercise.models.common :refer :all]
            [schema.core :as s]))

(s/defschema Dinosaur {:row (s/constrained s/Int #(<= min-boundary % max-boundary))
                       :col (s/constrained s/Int #(<= min-boundary % max-boundary))})

(s/defschema Robot {:row (s/constrained s/Int #(<= 0 % 49))
                    :col  (s/constrained s/Int #(<= 0 % 49))
                    :dirn (s/enum :north
                                  :west
                                  :south
                                  :east)})

;; Unfortunately Compojure API does not seem to support s/if construct of schema, hence schema itself is not exactly
;; precise and certain aspects of validation are provided by other components.

(s/defschema Action {:action (s/enum :move
                                     :turn
                                     :attack)
                     (s/optional-key :param) (s/enum :forward
                                                     :backward
                                                     :left
                                                     :right)})
