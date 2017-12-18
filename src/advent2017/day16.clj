(ns advent2017.day16
  (:require [clojure.string :as str]))

(defn s->int [s] (Integer/parseInt (str s)))

(defn spin [c n]
  (let [times (s->int n)]
    (into (drop-last times c) (reverse (take-last times c)))))

(defn swap [v a b]
  (let [index-a (s->int a)
        index-b (s->int b)
        vect (vec v)]
    (assoc vect index-a (vect index-b) index-b (vect index-a))))

(defn partner [v a b]
  (let [index-a (.indexOf v a)
        index-b (.indexOf v b)]
    (swap v index-a index-b)))

(defn process-input [input]
  (vec (str/split (str/trim input) #",")))

(defn parse-move [move]
  (let [name (first move)]
    {:move-func (cond (= name \s) spin
                      (= name \x) swap
                      (= name \p) partner)
     :params (str/split (apply str (rest move)) #"/")}))

(defn do-move [c move]
  (let [parsed-move (parse-move move)
        func (:move-func parsed-move)
        params (:params parsed-move)]
    (apply func c params)))

(defn day16 [input]
  (let [letters ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"]]
    (println "day 16 part 1:" (apply str (reduce do-move letters (process-input input))))))
