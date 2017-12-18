(ns advent2017.day16
  (:require [clojure.string :as str]))

(def p2times 1000000000)

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

(defn do-all-moves [letters moves]
  (reduce do-move letters moves))

(defn find-cycle-repetitions [letters moves times]
  (loop [i 0 l letters results []]
    (let [next-result (do-all-moves l moves)]
      (if (.contains results next-result) i
          (recur (inc i) next-result (conj results next-result))))))

(defn repeat-times [letters moves times]
  (loop [i 0 l letters]
    (if (= i times) l
        (recur (inc i) (do-all-moves l moves)))))

(defn day16 [input]
  (let [letters ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"]
        moves (process-input input)]
    (println "day 16 part 1:" (apply str (do-all-moves letters moves)))
    (let [times (mod p2times (find-cycle-repetitions letters moves p2times))]
      (println "day 16 part 2:" (apply str (repeat-times letters moves times))))))
