 (ns news-search.app
  (:require
    [news-search.search :as search]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.pprint :refer [pprint]]
    [clojure.data.json :as json]
    [ring.adapter.jetty :as jetty]
    [ring.util.response :refer [resource-response response content-type]]
    [ring.middleware.params :refer [wrap-params]])
  (:gen-class))

(defn search-handler [req]
  (let [q (get-in req [:params "q"])
        terms (str/split q #"\p{Z}")
        docs (search/search terms)]
    (-> (json/write-str results)
        response
        (content-type "application/json"))))

(defn test-handler [req]
    {:status 200
        :headers {"Content-Type" "text/clojure"}
        :body (with-out-str (pprint req))})

(defn main-handler [req]
  (case (:uri req)
    "/search" (search-handler req)
    (test-handler req)))

(def handler (wrap-params main-handler))

(defn -main [port]
  (jetty/run-jetty handler {:port (Integer/parseInt port)}))
