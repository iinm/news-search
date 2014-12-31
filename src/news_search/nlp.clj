(ns news-search.nlp
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer [pprint]])
  (:import [org.atilika.kuromoji Token Tokenizer])
  (:gen-class))

(defn tokenize [text-ja]
  (let [tokenizer (.. Tokenizer builder build)
        text->tokens #(.tokenize tokenizer %)
        token->word (fn [^Token t] (.getSurfaceForm t))]
    (map token->word (text->tokens text-ja))))
