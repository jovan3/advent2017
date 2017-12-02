(ns advent2017.day2
  (:require [clojure.string :as str]))

(defn tabnums->list [tabulated_num_string]
  (map #(Integer/parseInt %) (str/split tabulated_num_string #"\t")))

(defn day2 [input]
  (let [processed-input (str/split-lines input)
        row-lists (map tabnums->list processed-input)]
    (reduce + (map #(- (apply max %) (apply min %)) row-lists))))
