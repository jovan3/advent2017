(ns advent2017.day19
  (:require [clojure.string :as str]))

(defn process-input [input]
  (vec (map #(str/split % #"") (str/split-lines input))))

(defn map-kv [f coll]
  (reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))

(defn neighbors
  ([input position]
   (neighbors input position {:left [0 -1] :right [0 1] :up [-1 0] :down [1 0]}))
  ([input position deltas]
   (map-kv #(map + position %) deltas)))

(defn find-beginning [input]
  [0 (.indexOf (first input) "|")])

(defn next-position [world current direction]
  (direction (neighbors world current)))

(defn perpendicular-dirs [dir]
  (if (or (= dir :left) (= dir :right)) {:up [-1 0] :down [1 0]}
      {:left [0 -1] :right [0 1]}))

(defn path-neighbors [world neighbors]
  (filter #(and (not (nil? (get-in world (second %)))) (not= (get-in world (second %)) " ")) neighbors))

(defn next-direction [world position direction]
  (let [value (get-in world position)]
    (if (= value "+")
      (let [neigh (neighbors world position (perpendicular-dirs direction))]
        (key (first (path-neighbors world neigh))))
      direction)))

(defn invalid-pos? [world position]
  (let [[y x] position ydim (count world) xdim (count (first world)) value (get-in world position)]
    (or (> y ydim) (> x xdim) (< y 0) (< x 0) (nil? value) (= " " value))))
     
(defn walk [input init-position]
  (loop [pos init-position direction :down history []]
    (if (invalid-pos? input pos) history
        (let [next-dir (next-direction input pos direction)
              next-pos (next-position input pos next-dir)]
          (recur next-pos next-dir (conj history (get-in input pos)))))))

(defn day19 [input]
  (let [world (process-input input)
        start (find-beginning world)
        walk-history (walk world start)]
    (println "day 19 part 1:" (apply str (filter #(re-matches #"[A-Z]" %) walk-history)))
    (println "day 19 part 2:" (count walk-history))))
  
