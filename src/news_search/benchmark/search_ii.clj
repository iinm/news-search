(ns news-search.benchmark.search-ii
  (:require
    [news-search.data :as data]
    [news-search.benchmark.core :refer :all]
    [news-search.search-ii :refer :all])
  (:gen-class))

(defn -main []
  (measure search (str data/data-dirname "/benchmark/search-ii")))
