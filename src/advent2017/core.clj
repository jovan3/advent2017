(ns advent2017.core
  (:require [advent2017.day1 :as day1]
            [advent2017.day2 :as day2]
            [advent2017.day3 :as day3]
            [clojure.java.io :as io]))

(defn -main
  [& args]
  (println (day1/day1 (slurp (io/file (io/resource "day1_input")))))
  (println (day1/day1_2 (slurp (io/file (io/resource "day1_input")))))
  (println (day2/day2 (slurp (io/file (io/resource "day2_input")))))
  (println (day3/day3 265149)))
