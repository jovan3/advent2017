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

(defn make-knot [s length index]
  (let [[first-part last-part] (split-at length (shift s :left index))]
    (shift (concat (reverse first-part) last-part) :right index)))

(defn knot-hash [list input & {:keys [init-skip init-pos] :or {init-skip 0 init-pos 0}}]
  (loop [l list in input skip init-skip current init-pos]
    (let [length (first in)]
      (if (nil? length) {:result l :skip skip :position current}
          (recur (make-knot l length current) (rest in) (inc skip) (mod (+ current skip length) (count l)))))))

(defn str->lenghts [str]
  (concat (map int (seq str)) '(17, 31, 73, 47, 23)))

(defn do-rounds [input]
  (loop [list (init-list) i 0 skip 0 position 0]
    (if (= i 64) list
        (let [round-result (knot-hash list input :init-skip skip :init-pos position)]
          (recur (get round-result :result) (inc i) (get round-result :skip) (get round-result :position))))))

(defn dense-hash [sparse]
  (apply (partial format (apply str (repeat 16 "%02x"))) (map #(reduce bit-xor %) (partition 16 sparse))))

(defn knot [input]
  (dense-hash (do-rounds (str->lenghts input))))

(defn day10 [input]
  (let [knot-result (get (knot-hash (init-list) (process-input input)) :result)]
    (println "day 10 part 1:" (* (first knot-result) (second knot-result))))
  (println "day 10 part 2:" (knot (str/trim input))))
