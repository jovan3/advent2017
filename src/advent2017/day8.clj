(ns advent2017.day8
  (:require [clojure.string :as str]))

(defn str->int [str]
  (Long/parseLong str))

(defn comp-str->func [comp-str value]
  (fn [n]
      ((eval (read-string (str/replace comp-str #"!=" "not="))) n (str->int value))))

(defn parse-line [line]
  (let [line-parts (str/split line #" ")
        change-value (str->int (nth line-parts 2))]
    {:register (first line-parts)
     :operation (if (= "inc" (second line-parts)) #(+ % change-value) #(- % change-value))
     :reg-compare (nth line-parts 4)
     :comparison (comp-str->func (nth line-parts 5) (last line-parts))}))

(defn register-names [lines]
  (distinct (flatten (map (fn [line] [(first line) (nth line 4)]) (map #(str/split % #" ") lines)))))

(defn init-registers [register-names]
  (into {} (map (fn [r] [r 0]) register-names)))

(defn run [input-lines registers]
  (loop [current-line (first input-lines) remaining (rest input-lines) reg registers]
    (println reg)
    (println current-line)
    (if (= remaining '()) reg
        (let [parsed-line (parse-line current-line)
              reg-to-compare (get reg (:reg-compare parsed-line))
              register-value (get reg (:register parsed-line))]
          (if ((:comparison parsed-line) reg-to-compare)
            (recur (first remaining) (rest remaining) (update reg (:register parsed-line) (:operation parsed-line)))
            (recur (first remaining) (rest remaining) reg))))))

(defn mapmax [m]
  (get m (key (apply max-key val m))))

(defn day8 [input]
  (let [lines (str/split-lines input)
        registers (init-registers (register-names lines))]
    (let [result (run lines registers)]
      (println result)
      (mapmax result))))
