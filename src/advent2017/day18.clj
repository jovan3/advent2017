(ns advent2017.day18
  (:require [clojure.string :as str]))

(defn process-input [input] (str/split-lines input))

(defn init-registers []
  (into (sorted-map "snd" 0 "pc" 0) (zipmap (->>
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

(defn remainder [args regs]
  (setreg args regs #(rem %1 %2)))

(defn setval [args regs]
  (setreg args regs (fn [a b] b)))

(defn add [args regs]
  (setreg args regs +))

(defn mul [args regs]
  (setreg args regs *))

(defn snd [args regs]
  (assoc regs "snd" (value (first args) regs)))

(defn rcv [args regs]
  regs)

(defn jgz [args regs]
  (if (> (value (first args) regs) 0)
    (assoc regs "pc" (dec (+ (value "pc" regs) (value (second args) regs))))
    regs))

(defn get-func [func-name]
  (get {"mod" remainder
        "set" setval
        "mul" mul
        "add" add
        "snd" snd
        "rcv" rcv
        "jgz" jgz} func-name))

(defn exec-instruction [instruction regs]
  (let [[name & args] (str/split instruction #" ")]
    (update ((get-func name) args regs) "pc" inc)))

(defn run [instructions]
  (loop [regs (init-registers)]
    (let [pc (get regs "pc") instruction (nth instructions pc nil)]
      (if (or (nil? instruction) (= "rcv" (first (str/split instruction #" ")))) regs
          (let [next-regs (exec-instruction instruction regs)]
            (recur next-regs))))))
               
(defn day18 [input]
  (println "day 18 part 1:" (get (run (process-input input)) "snd")))
