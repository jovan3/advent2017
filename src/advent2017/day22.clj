(ns advent2017.day22
  (:require [clojure.string :as str]
            [advent2017.day21 :as day21]))

(def test-grid [["." "." "#"] ["#" "." "."] ["." "." "."]])

(def padding-multiplier 4000)

(def iterations 10000)

(def moves
  {[:north :right] {:offset [1 0] :direction :east}
   [:north :left] {:offset [-1 0] :direction :west}
   [:south :right] {:offset [-1 0] :direction :west}
   [:south :left] {:offset [1 0] :direction :east}
   [:east :right] {:offset [0 1] :direction :south}
   [:east :left] {:offset [0 -1] :direction :north}
   [:west :right] {:offset [0 -1] :direction :north}
   [:west :left] {:offset [0 1] :direction :south}})


(defn process-input [input]
  (mapv #(str/split % #"") (str/split-lines input)))

(defn nodes-row-padding [nodes-row size]
  (let [nodes-size (count nodes-row)
        empty-region (repeat size ".")]
    (vec (concat empty-region nodes-row empty-region))))

(defn empty-rows [nodes-size padding-size]
  (vec (repeat padding-size (vec (repeat (+ (* 2 padding-size) nodes-size) ".")))))

(defn place-initial-nodes-on-grid [nodes]
  (let [nodes-size (count nodes)
        padding-size (* nodes-size padding-multiplier)]
    (vec (concat
          (empty-rows nodes-size padding-size)
          (mapv #(nodes-row-padding % padding-size) nodes)
          (empty-rows nodes-size padding-size)))))

(defn flip [node]
  (if (= node "#") "." "#"))

(defn move [grid]
  (loop [g grid i 0 position [(quot (count grid) 2) (quot (count grid) 2)] direction :north bursts 0]
    (if (>= i iterations) bursts
        (let [node (get-in g position)
              next-turn (if (= node ".") :left :right)
              next-move (get moves [direction next-turn])
              next-position (map + position (reverse (:offset next-move)))
              next-direction (:direction next-move)
              total-bursts (if (= node ".") (inc bursts) bursts)]
          (recur (update-in g position flip) (inc i) next-position next-direction total-bursts)))))

(defn day22 [input]
  (println "day 22 part 1:" (move (place-initial-nodes-on-grid (process-input input)))))
