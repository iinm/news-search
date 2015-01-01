(ns news-search.search-ii
  (:require
    [news-search.data :as data]
    [news-search.search :as search]
    [news-search.nlp :as nlp]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.set :as set]
    [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn search [q-terms]
  (let [q-terms-split (map nlp/tokenize q-terms)
        q-tf (frequencies (apply concat q-terms-split))
        ii (data/load-inverted-index)]
    (->> (vals (select-keys ii (keys q-tf)))
         (apply set/union)
         (map (fn [id] {:id id :tfidf-map (data/id->tfidf id)}))
         (map (fn [doc]
                (assoc doc :score (search/cos-sim q-tf (:tfidf-map doc)))))
         (filter (comp pos? :score))
         (filter #(search/contain-some-phrases? (:tfidf-map %) q-terms-split))
         (map (fn [{:keys [id score]}] [id score]))
         (sort-by second >))))
