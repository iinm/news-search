(ns news-search.benchmark.summary
  (:require
    [news-search.data :as data]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn load-time [fname]
  (->> fname slurp str/split-lines
       rest
       (map (fn [line]
              (-> (str/split line #"\s") first Float/parseFloat)))))

(defn summ [fs]
  (let [query-avg
        (for [f fs
              :let [id (.getName f)
                    times (load-time f)]]
          [id (float (/ (reduce + times) (count times)))])]
    {:query-avg (sort-by first query-avg)
     :all-avg (float (/ (reduce + (map second query-avg)) (count query-avg)))}))

(defn -main [dirname]
  (->> dirname io/file file-seq
       (filter #(re-find #"\d{4}" (.getName %)))
       summ pprint))
