(ns advent2017.day6
  (:require [clojure.string :as str]))

(defn process-input [input]
  (into [] (map #(Integer/parseInt %) (str/split (str/trim input) #"\t"))))

(defn find-max [v]
  (let [maxval (apply max v)]
    {:index (.indexOf v maxval) :value maxval}))

(defn spread [val length start]
  (let [v (vec (repeat length 0))]
    (loop [index (mod (inc start) length), new-v v, placed 0]
      (if (= placed val)
        new-v
        (let [current-index-val (get new-v index)]
          (recur (mod (inc index) length) (assoc new-v index (inc current-index-val)) (inc placed)))))))

(defn reallocate-bank [v]
  (let [maxval (find-max v) index (:index maxval) max-bank (:value maxval)]
    (into [] (map + (spread max-bank (count v) index) (assoc v index 0)))))

(defn reallocate [v]
  (loop [iterations [v] current (reallocate-bank v)]
    (if (.contains iterations current)
      (conj iterations current)
      (recur (conj iterations current) (reallocate-bank current)))))
                             
(defn day6 [input]
  (println "day 6 part 1:" (dec (count (reallocate (process-input input))))))
