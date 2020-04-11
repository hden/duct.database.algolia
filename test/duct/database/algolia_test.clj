(ns duct.database.algolia-test
  (:require [clojure.test :refer :all]
            [duct.database.algolia :as algolia]
            [integrant.core :as ig]
            [jsonista.core :as jsonista])
  (:import [com.algolia.search Defaults DefaultSearchClient]))

(deftest algolia-default-object-mapper-test
  (algolia/inject-module! {})
  (let [object-mapper (Defaults/getObjectMapper)
        value {:hello "world"}]
    (testing "read-value"
      (is (= value (jsonista/read-value (jsonista/write-value-as-string value)
                                        object-mapper))))
    (testing "write-value"
      (is (= '("world") (vals (jsonista/read-value (jsonista/write-value-as-string value
                                                                                   object-mapper))))))))
