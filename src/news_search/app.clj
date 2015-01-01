 (ns news-search.app
  (:require
    [news-search.search-cache :as search]
    [news-search.data :as data]
    [clojure.java.io :as io]
    [clojure.string :as str]
    [clojure.pprint :refer [pprint]]
    [clojure.data.json :as json]
    [ring.adapter.jetty :as jetty]
    [ring.util.response :refer [resource-response response content-type]]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.resource :refer [wrap-resource]]
    [ring.middleware.content-type :refer [wrap-content-type]])
  (:gen-class))

(defn search-handler [req]
  (let [q (get-in req [:params "q"])
        terms (str/split q #"\p{Z}")
        bundle-text
        (fn [[id score]]
          {:id id :score score
           :text (data/id->text-mem id)})
        results (->> (search/search terms)
                     (take 25)
                     (map bundle-text))]
    (-> (json/write-str results)
        response
        (content-type "application/json"))))

(defn test-handler [req]
    {:status 200
        :headers {"Content-Type" "text/clojure"}
        :body (with-out-str (pprint req))})

(defn main-handler [req]
  (case (:uri req)
    "/" (-> "app/main.html" resource-response
            (content-type "text/html"))
    "/search" (search-handler req)
    (test-handler req)))

(def handler
  (-> main-handler
      wrap-params
      (wrap-resource "app")
      wrap-content-type))

(defn -main [port]
  (jetty/run-jetty handler {:port (Integer/parseInt port)}))
