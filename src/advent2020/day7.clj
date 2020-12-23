(ns advent2020.day7
  (:require [clojure.string :as str]))

(defn- parse-line
  [line]
  (if-let [[_ s os] (re-matches #"([a-z]+ [a-z]+) bags contain(( \d+ [a-z]+ [a-z]+ bags*,)* (\d+ [a-z]+ [a-z]+ bags*.))"
                                  line)]
    [s (into {} (for [o (str/split os #"\.|,")
                      :let [[_ n color] (re-matches #" *(\d+) ([a-z]+ [a-z]+) bags*" o)]]
                  [color (Long/parseLong n)]))]
    (let [[_ s] (re-matches #"([a-z]+ [a-z]+) bags contain no other bags."
                            line)]
      [s nil])))

(comment
 (re-matches #"([a-z]+ [a-z]+ bags) contain( \d+ [a-z]+ [a-z]+ bags*,)* (\d+ [a-z]+ [a-z]+ bags*.)"
             "clear gray bags contain 1 bright gray bag.")
 (re-matches #"([a-z]+ [a-z]+ bags) contain(( \d+ [a-z]+ [a-z]+ bags*,)* (\d+ [a-z]+ [a-z]+ bags*.))"
             "pale beige bags contain 4 wavy gray bags, 4 faded lime bags, 4 bright beige bags, 1 plaid violet bag.")
 (str/split " 4 wavy gray bags, 4 faded lime bags, 4 bright beige bags, 1 plaid violet bag."
            #"\.|,")
 (parse-line "clear gray bags contain 1 bright gray bag.")
 (parse-line "pale beige bags contain 4 wavy gray bags, 4 faded lime bags, 4 bright beige bags, 1 plaid violet bag.")
 (parse-line "dim teal bags contain no other bags."))

(let [lines (str/split-lines (slurp "resources/input7"))]
  (for [l lines]
    (parse-line l)))
