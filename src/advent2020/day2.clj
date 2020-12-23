(ns advent2020.day2
  (:require [clojure.string :as str]))

(defn- parse
  [text]
  (for [line (str/split-lines text)
        :let [[range c s] (str/split line #" ")
              range (->> (str/split range #"-")
                         (map #(Long/parseLong %)))
              c (first (seq c))]]
    [range c s]))

(defn solve1
  []
  (let [input (parse (slurp "resources/input2"))]
    (count (for [[[lower upper] c s :as i] input
                 :when (<= lower
                           (count (for [c' (seq s)
                                        :when (= c c')]
                                    c))
                           upper)]
             i))))

(defn solve2
  []
  (let [input (parse (slurp "resources/input2"))]
    (for [[[i1 i2] c s :as i] input
          :let [b1 (= c (nth s (dec i1)))
                b2 (= c (nth s (dec i2)))]
          :when (and (not= b1 b2)
                     (or b1 b2))]
      i)))

(count (solve2))
