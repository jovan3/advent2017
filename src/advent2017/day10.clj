(ns advent2017.day10
  (:require [clojure.string :as str]))

(defn process-input [input]
  (map #(Integer/parseInt %) (str/split (str/trim input)  #",")))

(defn init-list [] (range 0 256))

(defn shift-right [s]
  (conj (butlast s) (last s)))

(defn shift-left [s]
  (seq (conj (vec (rest s)) (first s))))

(defn shift [s direction times]
  (let [shift-fn (cond (= direction :left) shift-left
                       (= direction :right) shift-right)]
    (last (take (inc times) (iterate shift-fn s)))))

(defn knot-hash [list input]
  (loop [l list in input skip 0 current 0]
    (let [length (first in)]
      (if (nil? length) l
          (let [[first-part last-part] (split-at length (shift l :left current))]
            (recur (shift (into (concat (reverse first-part) last-part)) :right current) (rest in) (inc skip) (mod (+ current skip length) (count l))))))))

(defn day10 [input]
  (let [knot-result (knot-hash (init-list) (process-input input))]
    (println "day 10 part 1:" (* (first knot-result) (second knot-result)))))
