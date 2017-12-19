(ns advent2017.day17)

(def total-inserts 2017)

(defn insert [c item position]
  (into (into (drop position c) [item]) (reverse (take position c))))

(defn spinlock [input]
  (loop [i 2 c [0 1] position 1]
    (if (= i (inc total-inserts)) c
        (let [next-pos (inc (mod (+ position input) (count c)))]
          (recur (inc i) (insert c i next-pos) next-pos)))))

(defn day17 [input]
  (let [result (spinlock input)
        index (mod (inc (.indexOf result 2017)) 2018)]
    (println "day 17 part 1:" (nth result index))))
