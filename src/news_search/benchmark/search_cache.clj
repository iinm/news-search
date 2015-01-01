(ns news-search.benchmark.search-cache
  (:require
    [news-search.data :as data]
    [news-search.benchmark.core :refer :all]
    [news-search.search-cache :refer :all])
  (:gen-class))

(defn -main []
  (measure search (str data/data-dirname "/benchmark/search-cache")))
