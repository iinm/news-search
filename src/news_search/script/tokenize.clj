(ns news-search.script.tokenize
  (:require
    [news-search.nlp :as nlp]
    [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn -main [& args]
  (pprint (map nlp/tokenize args)))
