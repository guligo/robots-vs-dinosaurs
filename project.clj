(defproject robots-vs-dinosaurs "0.1.0-SNAPSHOT"
  :description "Solution to Nubank's robots versus dinosaurs problem"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [metosin/compojure-api "1.1.11"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.12.4"]
            [lein-cloverage "1.0.13"]
            [lein-midje "3.2.1"]
            [lein-codox "0.10.5"]]
  :ring {:handler com.nubank.exercise.app/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]
                        [midje "1.9.4"]]}})
