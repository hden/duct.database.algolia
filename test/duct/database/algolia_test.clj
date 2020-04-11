(ns duct.database.algolia-test
  (:require [clojure.test :refer :all]
            [duct.database.algolia :as algolia]
            [integrant.core :as ig]
            [jsonista.core :as jsonista])
  (:import [com.algolia.search Defaults DefaultSearchClient]))
