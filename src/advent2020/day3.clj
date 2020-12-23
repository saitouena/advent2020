(ns advent2020.day3
  (:require [clojure.string :as str]))

(defn solve1
  []
  (let [field (str/split-lines (slurp "resources/input3"))
        len (count (first field))
        index-field-pairs (map vector (range) field)]
    (for [[i line] index-field-pairs
          :let [position (mod (* 3 i) len)]
          :when (= \# (nth line position))]
      [i line])))

(count (solve1))

(defn- solve2*
  [step-right step-down]
  (let [field (str/split-lines (slurp "resources/input3"))
        len (count (first field))
        index-field-pairs (map vector (range) field)]
    (for [[i line] index-field-pairs
          :let [position (mod (* step-right (quot i step-down)) len)]
          :when (and (= 0 (mod i step-down)) (= \# (nth line position)))]
      [i line])))

(defn solve2
  []
  (map (comp count solve2*) [1 3 5 7 1] [1 1 1 1 2]))

(apply * (solve2))
