(defproject hden/duct.database.algolia "0.1.0"
  :description "Integrant methods for connecting to a Algolia index"
  :url "https://github.com/hden/duct.database.algolia"
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.4"]
                 [com.algolia/algoliasearch-apache "3.16.9"]
                 [integrant "0.10.0"]]
  :repl-options {:init-ns duct.database.algolia})
