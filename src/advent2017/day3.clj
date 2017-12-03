(ns advent2017.day3)

(defn square [n]
  (reduce * (repeat 2 n)))

(defn closest-odd-square [input]
  (loop [c 1]
    (let [sq (square c)]
      (if (<= (- input sq) 0)
        c
        (recur (inc (inc c)))))))
        

(defn find-side-offset [input square]
  (let [side-offset (mod input square)
        half-side (/ square 2)]
    (println "offset" side-offset)
    (if (> side-offset half-side)
      (- (dec square) (- square side-offset))
      (- (dec square) (dec side-offset)))))
      
(defn day3 [input]
  (let [closest-square (closest-odd-square input)]
    (println "closest square" closest-square)
    (println "day 3: " (find-side-offset input closest-square))))
  
;; (defn side-lengths [n_sides]
;;   (let [sides_ranges (map #(+ 2 %) (range n_sides))
;;         all_sides_ranges (conj sides_ranges 1)]
;;     (interleave all_sides_ranges all_sides_ranges)))

;; (defn find-side [input]
;;   (let [side-lengths (side-lengths 1000)]
;;     (loop [count 0]
;;       (let [first-num-step (reduce + (take count side-lengths))]
;;       (if (< (- input first-num-step) 0) [(dec count) (reduce + (take (dec count) side-lengths)) first-num-step]
;;           (recur (inc count)))))))
