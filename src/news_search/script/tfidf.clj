(ns news-search.script.tfidf
  (:require
    [news-search.data :refer :all]
    [clojure.java.io :as io]
    [clojure.string :as str])
  (:gen-class))

(defn tfidf [tf-map df-map]
  (->> (keys tf-map)
       (map (fn [term]
              [term {:tfidf (* (get-in tf-map [term :tf])
                               (inc (Math/log
                                      (float (/ num-docs (get df-map term))))))
                     :indices (get-in tf-map [term :indices])}]))
       (into {})))

(defn save-tfidf! [tfidf-map fname]
  (with-open [wrtr (io/writer fname)]
    (doseq [[term m] (sort-by (comp :tfidf second) > tfidf-map)
            :let [line
                  (format "%s\t%s\t%s\n"
                          term (:tfidf m) (str/join "," (:indices m)))]]
      (.write wrtr line))))

(defn -main []
  (dorun
    (let [df-map (load-df)]
      (map (fn [id]
             (save-tfidf! (tfidf (-> id id->tfpath load-tf) df-map)
                          (id->tfidfpath id)))
           doc-ids))))
