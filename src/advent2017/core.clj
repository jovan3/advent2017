(ns advent2017.core
  (:require [advent2017.day1 :as day1]
            [advent2017.day2 :as day2]
            [advent2017.day3 :as day3]
            [advent2017.day4 :as day4]
            [clojure.java.io :as io]))

(defn resource-to-string [resource_filename]
  (slurp (io/file (io/resource resource_filename))))

(defn -main
  [& args]
  (println (day1/day1 (resource-to-string "day1_input")))
  (println (day1/day1_2 (resource-to-string "day1_input")))
  (println (day2/day2 (resource-to-string "day2_input")))
  (println (day3/day3 265149))
  (println (day4/day4 (resource-to-string "day4_input"))))

