(ns advent2020.day6
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(defn solve1
  []
  (apply + (for [groups-of-answer (str/split (slurp "resources/input6") #"\n\n")]
             (->> (for [answer-of-each-person (str/split-lines groups-of-answer)]
                    (set answer-of-each-person))
                  (apply set/union)
                  count))))

(defn solve2
  []
  (apply + (for [groups-of-answer (str/split (slurp "resources/input6") #"\n\n")]
             (->> (for [answer-of-each-person (str/split-lines groups-of-answer)]
                    (set answer-of-each-person))
                  (apply set/intersection)
                  count))))

(solve2)