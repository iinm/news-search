(ns news-search.script.df
  (:require
    [news-search.data :refer :all]
    [clojure.java.io :as io]
    [clojure.string :as str])
  (:gen-class))

(defn count-df []
  (let [tf-file->termset
        (fn [f]
          (->> f slurp str/split-lines
               (map #(first (str/split % #"\t")))
               set))]
    (->> (map id->tfpath doc-ids)
         (map tf-file->termset)
         (map (partial apply list))
         flatten
         frequencies)))

(defn -main []
  (with-open [wrtr (io/writer df-fname)]
    (doseq [[term df] (sort-by second > (count-df))]
      (.write wrtr (str term "\t" df "\n")))))
