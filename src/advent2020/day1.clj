(ns advent2020.day1
  (:require
   [clojure.string :as str]))

(defn solve1
  []
  (let [xs (->> (slurp "resources/input1")
                str/split-lines
                (map #(Long/parseLong %)))]
    (for [x xs
          y xs
          :when (= 2020 (+ x y))]
      (* x y))))

(defn solve2
  []
  (let [xs (->> (slurp "resources/input1")
                str/split-lines
                (map #(Long/parseLong %)))]
    (for [x xs
          y xs
          z xs
          :when (= 2020 (+ x y z))]
      (* x y z))))

(solve2)