(ns news-search.script.search
  (:require
    [news-search.data :refer :all]
    [news-search.nlp :as nlp]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.set :as set]
    [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn cos-sim [q-tf doc-tfidf]
  (let [square (fn [x] (* x x))
        norm (fn [xs] (Math/sqrt (reduce + (map square xs))))]
    (/ (reduce + (for [[term freq] q-tf]
                   (* (get q-tf term) (or (get-in doc-tfidf [term :tf]) 0))))
       (* (norm (vals q-tf)) (norm (map :tf (vals doc-tfidf)))))))

(defn contain-phrase? [tfidf-map terms]
  (loop [[head & tail] terms
         prev-matches
         (map dec (get-in tfidf-map [head :indices]))]
    (let [indices (get-in tfidf-map [head :indices])
          matches (filter (set (map inc prev-matches)) indices)]
      (cond
        (empty? prev-matches) false
        (nil? head) true
        (empty? matches) false
        :else (recur tail matches)))))

(defn contain-some-phrases? [tfidf-map phrases]
  (some (partial contain-phrase? tfidf-map) phrases))

(defn -main [& q-terms]
  (let [q-terms-split (map nlp/tokenize q-terms)
        q-tf (frequencies (apply concat q-terms-split))
        results
        (->> doc-ids
             (map (fn [id] {:id id :tfidf-map (id->tfidf id)}))
             (map (fn [doc]
                    (assoc doc :score (cos-sim q-tf (:tfidf-map doc)))))
             (filter (comp pos? :score))
             (filter #(contain-some-phrases? (:tfidf-map %) q-terms-split))
             (map (fn [{:keys [id score]}] [id score]))
             (sort-by second >))]
    (do
      ;(pprint q-terms-split)
      (println "[検索結果]" (count results) "件見つかりました．")
      (doseq [[id score] results] (println (str id "\t" score))))))
