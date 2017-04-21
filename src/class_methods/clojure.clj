(ns class-methods.clojure
  (:import (clojure.scala.interop.class.methods TestClass)))

(defn -main [& _]
  (let [instance (TestClass.)]
    (println (.method1 instance))    ; prints 1
    (println (.method2 instance))    ; prints 2
    (println (.inc instance 2))      ; prints 3
    (println (.sum instance 2, 3))   ; prints 5
    (.sideEffect instance 2, 3)))    ; does a side effect
