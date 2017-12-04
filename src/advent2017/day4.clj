(ns advent2017.day4
  (:require [clojure.string :as str]))

(defn line-words [line]
  (str/split line #" "))

(defn num-lines-repeating-words [input line-function]
  (frequencies (map (fn [n] (some #(not= 1 %) n))
                    (->>
                     (str/split-lines input)
                     (map line-function)
                     (map frequencies)
                     (map vals)))))

(defn sorted-letter-words [line-string] (map sort (line-words line-string)))

(defn day4 [input]
  (println "day 4 part 1: " (get (num-lines-repeating-words input line-words) nil))
  (println "day 4 part 2: " (get (num-lines-repeating-words input sorted-letter-words) nil)))
