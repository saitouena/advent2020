(ns advent2020.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]))

(def sample "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\nbyr:1937 iyr:2017 cid:147 hgt:183cm\n\niyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\nhcl:#cfa07d byr:1929\n\nhcl:#ae17e1 iyr:2013\neyr:2024\necl:brn pid:760753108 byr:1931\nhgt:179cm\n\nhcl:#cfa07d eyr:2025 pid:166559648\niyr:2011 ecl:brn hgt:59in")

(defn solve1
  [text]
  (let [passports (str/split text #"\n\n")
        required-entry #{"byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid" #_"cid"}]
    (for [pass passports
          :let [pass (into {} (for [entry (str/split pass #"\n| ")]
                                (str/split entry #":")))]
          :when (set/subset? required-entry (set (keys pass)))]
      pass)))

(count (solve1 (slurp "resources/input4")))
(count (solve1 sample))

(defn- byr-validator
  [pass]
  (when-let [byr (get pass "byr")]
    (let [i (Long/parseLong byr)]
      (<= 1920 i 2002))))

(defn iyr-validator
  [pass]
  (when-let [iyr (get pass "iyr")]
    (let [i (Long/parseLong iyr)]
      (<= 2010 i 2020))))

(defn- eyr-validator
  [pass]
  (when-let [eyr (get pass "eyr")]
    (let [i (Long/parseLong eyr)]
      (<= 2020 i 2030))))

(defn- hgt-validator
  [pass]
  (when-let [hgt (get pass "hgt")]
    (when-let [[_ n unit] (re-matches #"^(\d+)(cm|in)$" hgt)]
      (let [n (Long/parseLong n)]
        (case unit
          "cm" (<= 150 n 193)
          "in" (<= 59 n 76)
          false)))))

(defn- hcl-validator
  [pass]
  (when-let [hcl (get pass "hcl")]
    (boolean (re-matches #"^#[0-9a-f]{6}$" hcl))))


(defn- ecl-validator
  [pass]
  (when-let [ecl (get pass "ecl")]
    (boolean (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))))

(defn- pid-validator
  [pass]
  (when-let [pid (get pass "pid")]
    (boolean (re-matches #"^\d{9}$" pid))))

(def ^:private validator
  (every-pred byr-validator                     ;;"byr"
              iyr-validator                     ;;"iyr"
              eyr-validator                     ;;"eyr"
              hgt-validator                     ;;"hgt"
              hcl-validator                     ;;"hcl"
              ecl-validator                     ;;"ecl"
              pid-validator                     ;;"pid"
              ))

(def valid-passports "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980\nhcl:#623a2f\n\neyr:2029 ecl:blu cid:129 byr:1989\niyr:2014 pid:896056539 hcl:#a97842 hgt:165cm\n\nhcl:#888785\nhgt:164cm byr:2001 iyr:2015 cid:88\npid:545766238 ecl:hzl\neyr:2022\n\niyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719")
(def invalid-passports "eyr:1972 cid:100\nhcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926\n\niyr:2019\nhcl:#602927 eyr:1967 hgt:170cm\necl:grn pid:012533040 byr:1946\n\nhcl:dab227 iyr:2012\necl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277\n\nhgt:59cm ecl:zzz\neyr:2038 hcl:74454a iyr:2023\npid:3556412378 byr:2007")

(defn solve2
  [text]
  (let [passports (str/split text #"\n\n")]
    (for [pass passports
          :let [pass (into {} (for [entry (str/split pass #"\n| ")]
                                (str/split entry #":")))]
          :when (validator pass)]
      pass)))

(count (solve2 sample))
(count (solve2 (slurp "resources/input4")))
(count (solve2 valid-passports))
(count (solve2 invalid-passports))
