(ns news-search.script.tf
  (:require
    [news-search.data :refer :all]
    [clojure.java.io :as io]
    [clojure.string :as str])
  (:gen-class))

(defn save-tf! [t-indices fname]
  (with-open [wrtr (io/writer fname)]
    (doseq [[term indices] (sort-by (comp count second) > t-indices)
            :let [line (format "%s\t%s\t%s\n"
                               term (count indices) (str/join "," indices))]]
      (.write wrtr line))))

(defn -main []
  (dorun
    (map (fn [id] (save-tf!
                    (-> id id->mcbpath load-mcb term-indices)
                    (id->tfpath id)))
         doc-ids)))
