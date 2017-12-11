(ns advent2017.day9
  (:require [clojure.string :as str]))

(defn preprocess-escapes [input]
  (str/replace input #"(!.)|(!$)" ""))

(defn remove-garbage [input]
  (str/replace (str/replace input #"<.*?>" "") #"," ""))

(defn stack [prev-stack item]
  (if (= item "{")
    (update prev-stack :size inc)
    (update (update prev-stack :total + (:size prev-stack)) :size dec)))

(defn day9 [input]
  (println "day 9 part 1:" (reduce stack {:size 0 :total 0} (str/split (remove-garbage (preprocess-escapes input)) #""))))
