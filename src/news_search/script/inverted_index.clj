(ns news-search.script.inverted-index
  (:require
    [news-search.data :refer :all]
    [clojure.java.io :as io]
    [clojure.set :as set])
  (:gen-class))

(defn inverted-index [doc-id]
  (zipmap (-> doc-id id->tfpath load-tf keys) (repeat #{doc-id})))

(defn -main []
  (with-open [wrtr (io/writer inverted-index-fname)]
    (binding [*print-dup* true *out* wrtr]
      (->> (map inverted-index doc-ids)
           (apply merge-with set/union)
           print))))
