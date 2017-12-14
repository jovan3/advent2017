(ns advent2017.day12
  (:require [clojure.string :as str]
            [advent2017.day7 :as day7]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]))

(defn parse-line [line]
  (let [line-parts (str/split line #" <-> ")
        vert-from (first line-parts)
        verts-to (str/split (last line-parts) #", ")]
    [vert-from verts-to]))

(defn day12 [input]
  (let [graph (->>
   (str/split-lines input)
   (map parse-line)
   (mapcat day7/make-edges)
   (apply uber/graph))]
    (println "day 12 part 1:" (count (alg/pre-traverse graph "0")))))
