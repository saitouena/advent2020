(ns advent2020.day23)

(def ^:private input [5 8 3 9 7 6 2 4 1])

(def ^:private sample [3 8 9 1 2 5 4 6 7])

(def ^:private ten 10)

(defn- my-dec
  [x]
  (let [y (dec x)]
    (if (< y 1)
      9
      y)))

(defn- step
  [current-idx cups]
  (let [threes (let [[front back] (split-at (inc current-idx) cups)]
                (take 3 (concat back front)))
        destination (->> (iterate my-dec (nth cups current-idx))
                         (remove (set threes))
                         (drop 1)
                         first)]
    [threes destination]))

(->> (iterate my-dec 3)
     (take 20))