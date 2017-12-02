(ns advent2017.day2
  (:require [clojure.string :as str]))

(defn tabnums->list [tabulated_num_string]
  (map #(Integer/parseInt %) (str/split tabulated_num_string #"\t")))

(defn noneq-evenly-div-pairs [row]
  (filter (complement nil?)
          (for [x row y row :when (not= x y)] (if (= 0 (rem x y)) [x y] nil))))

(defn row-evenly-div-pair-divided [row]
  (let [[[x y]] (noneq-evenly-div-pairs row)]
    (/ x y)))
    
(defn day2_1 [input]
  (reduce + (map row-evenly-div-pair-divided input)))

(defn day2 [input]
  (let [processed-input (str/split-lines input)
        row-lists (map tabnums->list processed-input)]
    (println "day 2 part 1: " (reduce + (map #(- (apply max %) (apply min %)) row-lists)))
    (println "day 2 part 2: " (day2_1 row-lists))))
