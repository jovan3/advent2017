(ns advent2017.day21
  (:require [clojure.string :as str]))

(def initial-matrix [["." "#" "."] ["." "." "#"] ["#" "#" "#"]])

(defn transpose [m]
  (apply mapv vector m))

(defn flip [m]
  (mapv #(vec (reverse %)) m))

(defn all-rotations [m]
  (loop [matrix m rotations [] i 0]
    (if (= i 8) rotations
        (let [op (if (= 0 (mod i 2)) flip transpose)]
          (recur (op matrix) (conj rotations matrix) (inc i))))))

(defn str->matrix [st]
  (mapv #(vec (str/split % #"")) (str/split st #"/")))

(defn process-line [line]
  (let [[from to] (str/split line #" => ")
        from-matrix (str->matrix from)
        from-rotations (all-rotations from-matrix)
        to-matrix (str->matrix to)]
    (zipmap from-rotations (repeat to-matrix))))

(defn process-input [input]
  (into {} (map process-line (str/split-lines input))))

(defn mprint [m]
  (println (apply str (interpose "\n" (map #(apply str %)  m))) "\n" ))

(defn get-chunk [m position size]
  (let [[x y] position]
    (vec (map #(subvec (nth m %) x (+ x size)) (range y (+ y size))))))

(defn chunk-matrix [m]
  (let [size (count m)
        chunk-size (if (= (mod size 2) 0) 2 3)
        chunks (/ size chunk-size)]
    (for [x (range chunks) y (range chunks)]
      [x (get-chunk m [(* y chunk-size) (* x chunk-size)] chunk-size)])))

(defn enhance-chunks [m rules]
  (let [chunked-matrix (chunk-matrix m)]
    (mapv (fn [chunk] [(first chunk) (get rules (second chunk))]) chunked-matrix)))

(defn merge-chunks [chunks]
  (if (= (count chunks) 1) (first chunks)
      (apply mapv (fn [& i] (vec (flatten (vector i)))) chunks)))

(defn map-kv [f coll]
  (reduce-kv (fn [m k v] (assoc m k (f v))) (empty coll) coll))

(defn merge-rows [row-groups]
  (vals (map-kv #(merge-chunks (map (fn [chunk] (second chunk)) %)) row-groups)))

(defn enhance [m rules]
  (let [enhanced-chunks (enhance-chunks m rules)
        row-groups (group-by #(first %) enhanced-chunks)]
      (apply concat (merge-rows row-groups))))

(defn day21 [input]
  (let [rulez (process-input input)
        enhanced (nth (iterate #(enhance % rulez) initial-matrix) 5)]
    (println "day 21 part 1:" (count (filter #(= "#" %) (flatten enhanced))))))
