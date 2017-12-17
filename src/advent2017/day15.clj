(ns advent2017.day15
  (:require [clojure.string :as str]))

(def limit 40000000)
(def factor-a 16807)
(def factor-b 48271)

(defn int->binstr [i]
  (str/replace (format "%32s" (Integer/toBinaryString i)) #" " "0"))

(defn next-gen-val [n factor]
  (rem (* n factor) 2147483647))

(defn match-val [a b]
  (let [bin-a (int->binstr a) bin-b (int->binstr b)]
    (if (= (subs bin-a 16 32) (subs bin-b 16 32)) 1
        0)))

(defn find-matches [input]
  (let [[gen-a gen-b] input]
    (loop [i 0 a gen-a b gen-b matches 0]
      (if (= i limit) matches
          (recur (inc i) (next-gen-val a factor-a) (next-gen-val b factor-b) (+ matches (match-val a b)))))))

(defn day15 [input]
  (println "day 15 part 1:" (find-matches input)))
