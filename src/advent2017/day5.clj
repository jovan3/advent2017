(ns advent2017.day5
  (:require [clojure.string :as str]))

(defn move [v]
  (loop [steps 0 current 0 jump-vector v]
    (let [jump-offset (get jump-vector current)]
      (if (nil? (get jump-vector (+ current jump-offset)))
        (inc steps)
        (recur (inc steps) (+ current jump-offset) (assoc jump-vector current (inc jump-offset))))))) 

(defn day5 [input]
  (println "day 5 part 1:" (move (into [] (map #(Integer/parseInt %) (str/split-lines input))))))
