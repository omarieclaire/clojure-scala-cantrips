(ns objects.clojure
  (:import (clojure.scala.interop.objects TestClass$)))

(defn -main [& _]
  (let [singleton-instance (TestClass$/MODULE$)]
    (println singleton-instance))) ; #object[clojure.scala.interop.objects.TestClass$]
