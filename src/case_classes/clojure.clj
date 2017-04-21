(ns case-classes.clojure
  (:import (clojure.scala.interop.case.classes TestClass)))

(defn -main [& _]

  (let [instance (TestClass. 1 2)
        another-instance (TestClass/apply 1 2)]

    (println (.a instance)) ; prints 1
    (println (.b instance)) ; prints 2

    (println (.toString instance))         ; prints TestClass(1,2)
    (println (.toString another-instance)) ; prints TestClass(1,2)

    (println (str instance)) ; prints TestClass(1,2)

    (println (.equals instance another-instance)) ; prints true
    (println (= instance another-instance))))     ; prints true
