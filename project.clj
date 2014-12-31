(defproject news-search "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.atilika.kuromoji/kuromoji "0.7.7"]]
  :main ^:skip-aot news-search.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repositories [["Atilika Open Source repository"
                  "http://www.atilika.org/nexus/content/repositories/atilika"]])
