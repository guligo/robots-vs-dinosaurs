(defproject robots-vs-dinosaurs "0.1.0-SNAPSHOT"
  :description "Solution to Nubank's robots versus dinosaurs problem"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.1.2"]]
  :plugins [[lein-ring "0.12.4"]
            [lein-cloverage "1.0.13"]]
  :ring {:handler com.nubank.exercise.rest/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
