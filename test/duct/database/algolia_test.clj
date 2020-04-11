(ns duct.database.algolia-test
  (:require [clojure.test :refer :all]
            [duct.database.algolia :as algolia]
            [integrant.core :as ig])
  (:import [com.algolia.search Defaults DefaultSearchClient]))
