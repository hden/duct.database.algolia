(ns duct.database.algolia
  (:require [integrant.core :as ig])
  (:import [com.algolia.search DefaultSearchClient]))

(defrecord Boundary [client index])

(defmethod ig/init-key :duct.database/algolia
  [_ {:as options :keys [app-id api-key index]}]
  (let [client (DefaultSearchClient/create app-id api-key)
        index  (.initIndex client index)]
    (->Boundary client index)))
