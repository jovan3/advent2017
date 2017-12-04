(ns advent2017.day4
  (:require [clojure.string :as str]))

(defn line-words [line]
  (str/split line #" "))

(defn day4 [input]
   (println "day 4: " (get (frequencies (map (fn [n] (some #(not= 1 %) n)) (map vals (map frequencies (map line-words (str/split-lines input)))))) nil)))
