(ns advent2017.day4
  (:require [clojure.string :as str]))

(defn line-words [line]
  (str/split line #" "))

(defn num-lines-repeating-words [input]
  (frequencies (map (fn [n] (some #(not= 1 %) n))
                    (->>
                     (str/split-lines input)
                     (map line-words)
                     (map frequencies)
                     (map vals)))))

(defn day4 [input]
  (println "day 4: " (get (num-lines-repeating-words input) nil)))
