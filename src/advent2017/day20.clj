(ns advent2017.day20
  (:require [clojure.string :as str]))

(def max-steps 1000)

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

(defn remove-colided [particles]
  (let [groups (group-by :p particles)
        remove-positions (or (keys (filter (fn [[k v]] (> (count v) 1)) groups)) '())]
    (filter #(not (.contains remove-positions (:p %))) particles)))

(defn remove-colisions [input]
  (loop [particles input t 0]
    (if (> t max-steps) particles
        (recur (doall (map move-particle (remove-colided particles))) (inc t)))))

(defn day20 [input]
  (let [world (process-input input)
        distances (map distance (nth (iterate #(doall (map move-particle %)) world) max-steps))]
    (println "day 20 part 1:" (.indexOf distances (apply min distances)))
    (println "day 20 part 2:" (count (remove-colisions world)))))
