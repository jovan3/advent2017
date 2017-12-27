(ns advent2017.day20
  (:require [clojure.string :as str]))

(defn point-str->vec [point-str]
  (vec (map #(Integer/parseInt %) (str/split point-str #","))))

(defn parse-line [line]
  (let [groups (re-find #"p=<(.+)>, v=<(.+)>, a=<(.+)>" line)]
    {:p (point-str->vec (second groups))
     :v (point-str->vec (nth groups 2))
     :a (point-str->vec (last groups))}))

(defn process-input [input]
  (map parse-line (str/split-lines input)))

(defn move-particle [particle]
  (let [velocity (doall (map + (:v particle) (:a particle)))
        position (doall (map + (:p particle) velocity))]
    (assoc particle :p position :v velocity)))

(defn distance [particle]
  (let [position (:p particle)]
    (reduce + (map #(Math/abs %) position))))

(defn day20 [input]
  (let [world (process-input input)
        distances (map distance (nth (iterate #(doall (map move-particle %)) world) 10000))]
    (println "day 20 part 1:" (.indexOf distances (apply min distances)))))
