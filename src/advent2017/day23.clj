(ns advent2017.day23
  (:require [clojure.string :as str]))

(defn process-input [input] (str/split-lines input))

(defn init-registers []
  (into (sorted-map "pc" 0) (zipmap (->>
                              (concat (range 97 123))
                              (map char)
                              (map str)) (repeat 0))))

(defn str->int [n]
  (Integer/parseInt (str n)))

(defn numstring? [s]
  (not (nil? (re-matches #"-?\d+" s))))

(defn value [val registers]
  (if (numstring? val) (Integer/parseInt val)
      (get registers val)))

(defn setreg [args regs setfunc]
  (let [register (first args)
        operand (value (second args) regs)]
    (assoc regs register (setfunc (value register regs) operand))))

(defn setval [args regs]
  (setreg args regs (fn [a b] b)))

(defn sub [args regs]
  (setreg args regs -))

(defn mul [args regs]
  (setreg args regs *))

(defn jnz [args regs]
  (if (not (= (value (first args) regs) 0))
    (assoc regs "pc" (dec (+ (value "pc" regs) (value (second args) regs))))
    regs))

(defn get-func [func-name]
  (get {"set" setval
        "sub" sub
        "mul" mul
        "jnz" jnz} func-name))

(defn exec-instruction [instruction regs]
  (let [[name & args] (str/split instruction #" ")]
    (update ((get-func name) args regs) "pc" inc)))

(defn mul? [instruction]
  (= "mul" (first (str/split instruction #" "))))

(defn run [instructions]
  (loop [regs (init-registers) mul 0]
    (let [pc (get regs "pc") instruction (nth instructions pc nil)]
      (if (nil? instruction) mul
          (let [next-regs (exec-instruction instruction regs)
                mul-calls (if (mul? instruction) (inc mul) mul)]
            (recur next-regs mul-calls))))))

(defn day23 [input]
  (println "day 23 part 1:" (run (process-input input))))
