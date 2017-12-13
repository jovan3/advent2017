(ns advent2017.day11
  (:require [clojure.string :as str]))

(defn process-input [input]
  (str/split (str/trim input) #","))

(defn direction [label]
  (cond (= label "n") '(0 1 -1)
        (= label "ne") '(1 0 -1)
        (= label "nw") '(-1 1 0)
        (= label "s") '(0 -1 1)
        (= label "se") '(1 -1 0)
        (= label "sw") '(-1 0 1)))

(defn move [input]
  (loop [current '(0 0 0) in input]
    (println current)
    (let [next-dir-label (first in)]
      (if (nil? next-dir-label) current
          (recur (map + current (direction next-dir-label)) (rest in))))))

(defn distance [point]
  (/ (+ (Math/abs (first point)) (Math/abs (second point)) (Math/abs (last point))) 2))

(defn day11 [input]
  (println "day 11 part 1:" (distance (move (process-input input)))))
