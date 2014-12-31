(ns news-search.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [news-search.core :refer :all]))

(deftest load-mcb-test
  (pprint
    (->> "txt/991231022.mcb" io/resource io/file
         load-mcb
         term-indices
         (#(save-tf! % "")))))
