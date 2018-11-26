(defproject robots-vs-dinosaurs "1.0.0"
  :description "Solution to Nubank's robots versus dinosaurs problem"
  :min-lein-version "2.0.0"
  :jvm-opts ["-Xmx128m"]
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [metosin/compojure-api "1.1.11"]
                 [hiccup "1.0.5"]
                 [org.clojure/tools.logging "0.4.1"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler com.nubank.exercise.app/app}
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [ring/ring-mock "0.3.2"]
                                  [midje "1.9.4"]]
                   :plugins [[lein-midje "3.2.1"]
                             [lein-cloverage "1.0.13"]
                             [lein-codox "0.10.5"]]}})
