(defproject news-search "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.atilika.kuromoji/kuromoji "0.7.7"]
                 [org.clojure/data.json "0.2.5"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]]
  :main ^:skip-aot news-search.app
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :repositories [["Atilika Open Source repository"
                  "http://www.atilika.org/nexus/content/repositories/atilika"]])
