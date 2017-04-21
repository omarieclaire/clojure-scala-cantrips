(ns apply-method.clojure
  (:import (clojure.scala.interop.apply.method TestClass)))

(defn -main [& _]

  (let [instance-via-apply (TestClass/apply 4)]
    (println instance-via-apply))) ; prints object[clojure.scala.interop.apply.method.TestClass
