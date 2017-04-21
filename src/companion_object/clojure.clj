(ns companion-object.clojure
  (:import (clojure.scala.interop.companion.object TestClass$)))

(defn -main [& _]
  (let [singleton-instance (TestClass$/MODULE$)]
    (println singleton-instance))) ; #object[clojure.scala.interop.companion.object.TestClass$]
