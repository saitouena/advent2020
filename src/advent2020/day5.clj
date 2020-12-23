(ns advent2020.day5
  (:require [clojure.string :as str]))

(int (/ (inc 1) 2))

(defn- round-up-avg
  [x y]
  (int (/ (+ x y 1) 2)))

(defn- round-down-avg
  [x y]
  (int (/ (+ x y) 2)))

(defn- decode-row
  [cs]
  (loop [[c & cs' :as cs] cs
         lb 0
         ub 128]
    (if-not (seq cs)
      lb
      (case c
        \F (recur cs' lb (round-up-avg lb ub))
        \B (recur cs' (round-up-avg lb ub) ub)
        (assert false)))))

(defn- decode-col
  [cs]
  (loop [[c & cs' :as cs] cs
         lb 0
         ub 8]
    (if-not (seq cs)
      lb
      (case c
        \R (recur cs' (round-up-avg lb ub) ub)
        \L (recur cs' lb (round-up-avg lb ub))
        (assert false)))))

(defn- decode
  [s]
  (let [[row col] (split-at 7 s)]
    [(decode-row row) (decode-col col)]))

(defn- seet->id
  [[row col]]
  (+ (* row 8) col))

(defn solve1
  [text]
  (let [lines (str/split-lines text)]
    (apply max (map (comp seet->id decode) lines))))

(defn solve2
  [text]
  (let [lines (str/split-lines text)]
    (map (comp decode) lines)))

(comment
 (decode "BFFFBBFRRR")
 (decode "FFFBBBFRRR")
 (decode "BBFFBBFRLL")
 (solve1 (slurp "resources/input5"))
 (sort (solve2 (slurp "resources/input5")))
 (->> (solve2 (slurp "resources/input5"))
      (group-by first)
      vals
      (remove #(= 8 (count %)))))
;; answer is [68 4]
(seet->id [68 4])