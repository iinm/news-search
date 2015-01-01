(ns news-search.benchmark.search
  (:require
    [news-search.data :as data]
    [news-search.benchmark.core :refer :all]
    [news-search.search :refer :all])
  (:gen-class))

(defn -main []
  (measure search (str data/data-dirname "/benchmark/search")))
