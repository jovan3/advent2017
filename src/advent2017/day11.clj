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


(defn distance [point]
  (/ (+ (Math/abs (first point)) (Math/abs (second point)) (Math/abs (last point))) 2))

(defn move [input]
  (loop [current '(0 0 0) in input max-distance 0]
    (let [next-dir-label (first in)]
      (if (nil? next-dir-label) {:max-distance max-distance :distance (distance current)}
          (let [next-point (map + current (direction next-dir-label))
                next-distance (distance next-point)]
            (recur next-point (rest in) (max next-distance max-distance)))))))

(defn day11 [input]
  (let [result (move (process-input input))]
    (println "day 11 part 1:" (:distance result))
    (println "day 11 part 2:" (:max-distance result))))
