(ns modex.minimal.core
 ^{:author "Bader Szabolcs"}
  (:gen-class)  
  (:require [modex.mcp.protocols :as mcp]
            [modex.mcp.server    :as server]
            [modex.mcp.tools     :as tools]))

;; Define a tools map with two entries: `hello` and `inc`
(def minimal-tools
  (tools/tools
    (hello
      "Returns 'Hello, world!'"
      []
      "Hello, world!")
    (inc
      "Increments a number by 1"
      [{:keys [n]
        :type {n :number}
        :doc  {n "Number to increment"}}]
      ;; wrap the result in a vector of pr-str'd values
      [(pr-str (clojure.core/inc n))])))

(defn make-server []
  (server/->server
    {:name       "Minimal MCP Server"
     :version    "0.1.0"
     :tools      minimal-tools
     :initialize (fn [_] nil)
     :prompts    nil
     :resources  nil}))

(defn -main [& _]
  (server/start-server! (make-server)))
