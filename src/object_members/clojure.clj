(ns object-members.clojure
  (:import (clojure.scala.interop.object.members TestClass TestClass$)))

(defn -main [& _]

  (let [object-instance (TestClass$/MODULE$)]

    (println (TestClass/inc 1))        ;prints 2
    (println (.inc object-instance 1)) ;prints 2

    (println (TestClass/immutableField))        ;prints 0
    (println (.immutableField object-instance)) ;prints 0

    (println (TestClass/mutableField))          ;prints 1
    (println (.mutableField object-instance))   ;prints 1

    (TestClass/mutableField_$eq 3)              ;sets the mutable value to 3 through static method
    (println (TestClass/mutableField))          ;prints 3
    (println (.mutableField object-instance))   ;prints 3

    (.mutableField_$eq object-instance 4)       ;sets the mutable value to 4 through object-instance method
    (println (TestClass/mutableField))          ;prints 4
    (println (.mutableField object-instance)))) ;prints 4
