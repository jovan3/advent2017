(ns advent2017.day13
  (:require [clojure.string :as str]))

(defn move-s [s]
  (let [{position :position
         direction :direction
         length :length} s]
    (let [next-dir (cond (and (= position 0) (= direction :up)) :down
                         (and (= position (dec length)) (= direction :down)) :up
                         :else direction)]
      {:position (if (= next-dir :up) (dec position) (inc position))
       :direction next-dir
       :length length})))

(defn init-layer [[layer range]]
  (let [l (Integer/parseInt layer)
        r (Integer/parseInt range)]
    [l {:position 0 :direction :down :length r}]))

(defn process [input]
  (apply sorted-map
         (->>
          (str/split-lines input)
          (map #(str/split % #": "))
          (mapcat #(init-layer %)))))

(defn layer-severity [position layer-s]
  (if (nil? layer-s) 0
      (cond
        (and (= (:position layer-s 1) 1) (= (:direction layer-s) :down)) (* position (:length layer-s))
        :else 0)))

(defn next-state [state]
  (reduce-kv (fn [m k v] (assoc m k (move-s v))) (empty state) state))

(defn move-packet [initial-state]
  (loop [state initial-state position 0 caught []]
    (if (> position (key (last initial-state))) caught
        (recur (next-state state) (inc position) (conj caught (layer-severity position (get state position)))))))

(defn find-min-severity [initial-state]
  (loop [s (next-state initial-state) i 0]
    (let [severities (move-packet s)
          total (reduce + severities)]
      (if (= total 0) i
          (recur (next-state s) (inc i))))))

(defn day13 [input]
  (let [severities (move-packet (next-state (process input)))]
    (println "day 13 part 1:" (reduce + severities))
    (find-min-severity (process input))))
