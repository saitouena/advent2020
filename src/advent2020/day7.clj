(ns advent2020.day7
  (:require [clojure.string :as str]
            [clojure.set :as set]))

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

(defn- parsed->graph
  [parsed]
  (into {} (for [[k vs] (group-by first (apply concat (for [[k v] parsed]
                                                        (for [to (keys v)]
                                                          [to k]))))]
             [k (set (map second vs))])))

(defn- parse
  [text]
  (into {} (let [lines (str/split-lines text)]
             (for [l lines]
               (parse-line l)))))

(defn traverse
  [graph node]
  (when-let [nexts (get graph node)]
    (apply set/union nexts (map (partial traverse graph) nexts))))

(defn solve1
  [text]
  (let [parsed (parse text)
        graph (parsed->graph parsed)]
    (count (traverse graph "shiny gold"))))

(comment
 (-> (slurp "resources/input7")
     parse
     info->graph
     (get "shiny gold"))
 (solve1 (slurp "resources/input7"))
 (-> (slurp "resources/input7")
     parse))

(defn- count-bags
  [parsed bag]
  (apply + 1
         (for [[k n] (get parsed bag)]
           (* n (count-bags parsed k)))))

(defn solve2
  [text]
  (let [parsed (parse text)]
    (dec (count-bags parsed "shiny gold"))))

(def an-example "light red bags contain 1 bright white bag, 2 muted yellow bags.\ndark orange bags contain 3 bright white bags, 4 muted yellow bags.\nbright white bags contain 1 shiny gold bag.\nmuted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\nshiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\ndark olive bags contain 3 faded blue bags, 4 dotted black bags.\nvibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\nfaded blue bags contain no other bags.\ndotted black bags contain no other bags.")

(def another-example "shiny gold bags contain 2 dark red bags.\ndark red bags contain 2 dark orange bags.\ndark orange bags contain 2 dark yellow bags.\ndark yellow bags contain 2 dark green bags.\ndark green bags contain 2 dark blue bags.\ndark blue bags contain 2 dark violet bags.\ndark violet bags contain no other bags.")

(comment
 (solve2 an-example)
 (solve2 another-example)
 (solve2 (slurp "resources/input7")))
