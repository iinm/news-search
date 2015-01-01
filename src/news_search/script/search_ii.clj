(ns news-search.script.search-ii
  (:require
    [news-search.search-ii :refer :all])
  (:gen-class))

(defn -main [& q-terms]
  (let [results (search q-terms)]
    (do
      (println "[検索結果]" (count results) "件見つかりました．")
      (println "-----------------------------")
      (doseq [[id score] results] (println (str id "\t(" score ")"))))))
