(ns n-ary-constructors.clojure
  (:import (clojure.scala.interop.nary.constructors TestClass)))

(defn -main [& _]

  (let [instance1 (TestClass. 1 2)
        instance2 (TestClass. 2)
        instance3 (TestClass.)
        instance4 (TestClass. "4")]

    (println (.a instance1)) ; 1
    (println (.b instance1)) ; 2

    (println (.a instance2)) ; 2
    (println (.b instance2)) ; 0

    (println (.a instance3)) ; 0
    (println (.b instance3)) ; 0

    (println (.a instance4))   ; 4
    (println (.b instance4)))) ; 4