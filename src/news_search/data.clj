(ns news-search.data
  (:require
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.pprint :refer [pprint]])
  (:gen-class))

(def data-dirname (-> "data.path" io/resource slurp str/trim))

(def doc-ids
  (->> (str data-dirname "/txt") io/file file-seq
       (map #(.getName %))
       (filter #(.endsWith % ".txt"))
       (map #(str/replace % #".txt$" ""))))

(def num-docs (count doc-ids))

(defn id->mcbpath [id]
  (format "%s/txt/%s.mcb" data-dirname id))

(defn id->tfpath [id]
  (format "%s/tf/%s.tf" data-dirname id))

(def df-fname (str data-dirname "/all_df"))

(defn id->tfidfpath [id]
  (format "%s/tfidf/%s.tfidf" data-dirname id))

(def inverted-index-fname
  (str data-dirname "/inverted_index"))

(defn load-mcb [file]
  (letfn [(parse-line [line]
            (let [xs (str/split line #"\t|,")
                  fields (list :surf :pos :pos1 :pos2 :pos3
                               :infl :conj :orig :read :pron)]
              (if (= "EOS" (first xs))
                {:pos :EOS}
                (zipmap fields xs))))]
                ;(into {} (map vector fields xs)))))]
                ;(interleave fields xs))))]
    (with-open [rdr (-> file io/reader)]
      (doall
        (->> (line-seq rdr)
             (map parse-line))))))

(defn term-indices
  "-> [[term1 '(2 5 20)] [term2 '(4 7)] ...]"
  [term-seq]
  (let [term-indexed (for [[idx term] (map-indexed vector term-seq)]
                       (assoc term :idx idx))
        content? (fn [{:keys [pos pos1 orig]}]
                   (and
                     (contains? #{"名詞" "動詞" "形容詞" "未知語"} pos)
                     ((complement contains?) #{"非自立" "接尾" "代名詞"} pos1)
                     (not= "する" orig)))]
    (->> (filter content? term-indexed)
         (map (fn [{:keys [orig surf] :as t}]
                (if (= orig "*") (assoc t :orig surf) t)))
         (group-by :orig)
         (map (fn [[orig terms]] [orig (map :idx terms)])))))

(defn toInt [s] (Integer/parseInt (str s)))
(defn toFloat [s] (Float/parseFloat (str s)))

(defn load-df []
  (->> df-fname slurp str/split-lines
       (map #(str/split % #"\t"))
       (map (fn [[term df]] [term (toInt df)]))
       (into {})))

(defn load-tf [tf-file]
  (->> tf-file slurp str/split-lines
       (map #(str/split % #"\t"))
       (map (fn [[term tf indices]]
              [term {:tf (toFloat tf)
                     :indices (map toInt (str/split indices #","))}]))
       (into {})))

(defn id->tfidf [id] (-> id id->tfidfpath load-tf))
(def id->tfidf-mem (memoize id->tfidf))

(defn id->text [id]
  (->> (format "%s/txt/%s.txt" data-dirname id)
       slurp str/trim (take 200) (apply str)))

(def id->text-mem (memoize id->text))

(defn load-inverted-index []
  (with-open [r (-> inverted-index-fname
                    io/reader java.io.PushbackReader.)]
    (read r)))

(def load-inverted-index-mem
  (memoize load-inverted-index))
