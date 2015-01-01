(ns news-search.benchmark.search-cache-pmap
  (:require
    [news-search.data :as data]
    [news-search.benchmark.core :refer :all]
    [news-search.search-cache-pmap :refer :all])
  (:gen-class))

(defn -main []
  (measure search (str data/data-dirname "/benchmark/search-cache-pmap"))
  (System/exit 0))
