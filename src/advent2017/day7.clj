(ns advent2017.day7
  (:require [clojure.string :as str]
            [ubergraph.core :as uber]))

(defn extract-vert-from [line-parts]
  (first (re-seq #"[a-z]+" (first line-parts))))

(defn extract-verts-to [line-parts]
  (if (> (count line-parts) 1)
    (str/split (second line-parts) #", ")
    []))
  
(defn parse-line [line]
  (let [line-parts (str/split line #" -> ")
        vert-from-part (extract-vert-from line-parts)
        verts-to-part (extract-verts-to line-parts)]
    [vert-from-part verts-to-part]))

(defn make-edges [parsed-line]
  (let [[vert-from verts-to] parsed-line]
    (map (fn [n] [vert-from n]) verts-to)))

(defn day7 [input]
  (->>
   (str/split-lines input)
   (map parse-line)
   (mapcat make-edges)
   (apply uber/graph)
   (uber/viz-graph)))
