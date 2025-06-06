(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'baader/mininal-mcp-example)
(def version "0.3.1")
(def class-dir "target/classes")
(def basis (b/create-basis {:project "deps.edn"}))
(def uber-file (format "target/%s-%s-%s.jar" (namespace lib) (name lib) version))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/copy-dir {:src-dirs   ["src" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis     basis
                  :src-dirs  ["src"]
                  :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis     basis
           :main      'modex.minimal.core})
  (println (str "Compiled uberjar to path: " uber-file)))