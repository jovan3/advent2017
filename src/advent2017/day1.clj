(ns advent2017.day1
  (:require [clojure.string :as str]))

(defn calc [digits]
  (reduce +
          (flatten (map butlast
                        (filter #(> (count %) 1) (partition-by identity digits))))))

(defn str->digits [string]
  (map #(Integer/parseInt %) (butlast (str/split string #""))))  

(defn circular [list]
  (if (= (first list) (last list))
    (concat list (first (partition-by identity list)))))

(defn day1 [input]
  (let [processed-input (circular (str->digits input))]
    (println "day 1 result:" (calc processed-input))))

(defn day1_2 [input]
  (let [processed-input (str->digits input)
        split-input (split-at (/ (count processed-input) 2) processed-input)]
    (println "day 1 part 2 result:" (* 2 (reduce + (map #(if (= %1 %2) %1 0) (first split-input) (last split-input)))))))
