(ns advent2017.day14
  (:import (org.apache.commons.codec.binary Hex))
  (:import (org.apache.commons.codec.binary BinaryCodec))
  (:require [advent2017.day10 :as day10]))

(defn gen-row-strings [input]
  (map #(str input "-" %) (range 128)))

(defn digest->binary [digest]
  (BinaryCodec/toAsciiString (Hex/decodeHex digest)))

(defn day14 [input]
  (println "day 14 part 1:" (count (->>
                                    (gen-row-strings input)
                                    (map day10/knot)
                                    (mapcat digest->binary)
                                    (filter #(= \1 %))))))
