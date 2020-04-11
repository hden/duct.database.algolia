(ns duct.database.algolia
  (:require [integrant.core :as ig]
            [jsonista.core :as jsonista])
  (:import [com.algolia.search Defaults DefaultSearchClient]
           [com.fasterxml.jackson.databind
             JsonSerializer
             ObjectMapper
             module.SimpleModule]
           [jsonista.jackson
             DateSerializer
             FunctionalKeyDeserializer
             FunctionalSerializer
             KeywordSerializer
             KeywordKeyDeserializer
             PersistentHashMapDeserializer
             PersistentVectorDeserializer
             SymbolSerializer
             RatioSerializer
             FunctionalKeywordSerializer]))

(defrecord Boundary [client index])

; Copied from https://github.com/metosin/jsonista/blob/master/src/clj/jsonista/core.clj#L72-L99
(defn- clojure-module
  "Create a Jackson Databind module to support Clojure datastructures.
  See [[object-mapper]] docstring for the documentation of the options."
  [{:keys [encode-key-fn decode-key-fn encoders date-format]
    :or {encode-key-fn true, decode-key-fn true}}]
  (doto (SimpleModule. "Clojure")
    (.addDeserializer java.util.List (PersistentVectorDeserializer.))
    (.addDeserializer java.util.Map (PersistentHashMapDeserializer.))
    (.addSerializer clojure.lang.Keyword (KeywordSerializer. false))
    (.addSerializer clojure.lang.Ratio (RatioSerializer.))
    (.addSerializer clojure.lang.Symbol (SymbolSerializer.))
    (.addSerializer java.util.Date (if date-format
                                     (DateSerializer. date-format)
                                     (DateSerializer.)))
    (as-> module
          (doseq [[type encoder] encoders]
            (cond
              (instance? JsonSerializer encoder) (.addSerializer module type encoder)
              (fn? encoder) (.addSerializer module type (FunctionalSerializer. encoder))
              :else (throw (ex-info
                             (str "Can't register encoder " encoder " for type " type)
                             {:type type, :encoder encoder})))))
    (cond->
      (true? decode-key-fn) (.addKeyDeserializer Object (KeywordKeyDeserializer.))
      (fn? decode-key-fn) (.addKeyDeserializer Object (FunctionalKeyDeserializer. decode-key-fn))
      (true? encode-key-fn) (.addKeySerializer clojure.lang.Keyword (KeywordSerializer. true))
      (fn? encode-key-fn) (.addKeySerializer clojure.lang.Keyword (FunctionalKeywordSerializer. encode-key-fn)))))

(defn inject-module! [options]
  (let [object-mapper (Defaults/getObjectMapper)]
    (doto object-mapper
      (.registerModule (clojure-module options)))))

(defmethod ig/init-key :duct.database/algolia
  [_ {:as options :keys [app-id api-key index]}]
  (inject-module! options)
  (let [client (DefaultSearchClient/create app-id api-key)
        index  (.initIndex client index)]
    (->Boundary client index)))
