(ns advent2017.day5
  (:require [clojure.string :as str]))

(defn move [v calculate-new-offset]
  (loop [steps 0 current 0 jump-vector v]
    (let [jump-offset (get jump-vector current)
          next (+ current jump-offset)]
      (if (nil? (get jump-vector next))
        (inc steps)
        (let [changed-offset (calculate-new-offset jump-offset)]
          (recur (inc steps) next (assoc jump-vector current changed-offset)))))))

(defn calculate-part2-offset [jump-offset]
  (if (>= jump-offset 3) (dec jump-offset) (inc jump-offset)))

(defn process-input [input]
  (into [] (map #(Integer/parseInt %) (str/split-lines input))))

(defn day5 [input]
  (println "day 5 part 1:" (move (process-input input) #(inc %)))
  (println "day 5 part 2:" (move (process-input input) calculate-part2-offset)))
