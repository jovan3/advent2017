(ns advent2017.day15_2
  (:require [advent2017.day15 :as day15]))

(def limit 5000000)

(defn find-next-divisible [n factor div]
  (loop [current (day15/next-gen-val n factor)]
    (if (= (mod current div) 0) current
        (recur (day15/next-gen-val current factor)))))

(defn generator [n factor div]
  (let [divisible (find-next-divisible n factor div)]
    (lazy-seq (cons divisible (generator divisible factor div)))))

(defn find-matches [input]
  (let [[init-value-a init-value-b] input
        generator-a (generator init-value-a day15/factor-a 4)
        generator-b (generator init-value-b day15/factor-b 8)]
    (loop [i 0 matches 0 gen-a generator-a gen-b generator-b]
      (if (= i limit) matches
          (recur (inc i) (+ matches (day15/match-val (first gen-a) (first gen-b))) (rest gen-a) (rest gen-b))))))

(defn day15_2 [input]
  (println "day 15 part 2:" (find-matches input)))
