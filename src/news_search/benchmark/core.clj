(ns news-search.benchmark.core
  (:require
    [news-search.data :as data]
    [clojure.string :as str])
  (:gen-class))

(def queries
  (->> (str data/data-dirname "/queries.txt")
       slurp str/split-lines
       (map (fn [line]
              (let [[qstr id] (str/split line #"\s>")]
                [id qstr])))
       (into {})))

(defn search-time [searcher q-terms]
  (->> (with-out-str (time (dorun (searcher q-terms))))
       (re-find #"[.\d]+ msecs")))

(defn measure [searcher result-dir]
  (doseq [[id qstr] queries
          :let [q-terms (str/split qstr #"\s")
                out (str result-dir "/" id)]]
    (->> (repeatedly 11 #(search-time searcher q-terms))
         (str/join "\n")
         (spit out))))
