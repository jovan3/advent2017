(ns advent2017.core
  (:require [advent2017.day1 :as day1]
            [advent2017.day2 :as day2]
            [advent2017.day3 :as day3]
            [advent2017.day4 :as day4]
            [advent2017.day5 :as day5]
            [advent2017.day6 :as day6]
            [advent2017.day7 :as day7]
            [advent2017.day8 :as day8]
            [advent2017.day9 :as day9]
            [advent2017.day10 :as day10]
            [advent2017.day11 :as day11]
            [advent2017.day12 :as day12]
            [advent2017.day13 :as day13]
            [clojure.java.io :as io]))

(defn resource-to-string [resource_filename]
  (slurp (io/file (io/resource resource_filename))))

(defn -main
  [& args]
  (comment (println (day1/day1 (resource-to-string "day1_input")))
  (println (day1/day1_2 (resource-to-string "day1_input")))
  (println (day2/day2 (resource-to-string "day2_input")))
  (println (day3/day3 265149))
  (println (day4/day4 (resource-to-string "day4_input")))
  (println (day5/day5 (resource-to-string "day5_input")))
  (println (day6/day6 (resource-to-string "day6_input")))
  (println (day7/day7 (resource-to-string "day7_input")))
  (println (day8/day8 (resource-to-string "day8_input")))
  (println (day9/day9 (resource-to-string "day9_input")))
  (println (day10/day10 (resource-to-string "day10_input")))
  (println (day11/day11 (resource-to-string "day11_input")))
  (println (day12/day12 (resource-to-string "day12_input"))))
  (println (day13/day13 (resource-to-string "day13_input"))))
  

