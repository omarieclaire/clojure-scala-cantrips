(ns default-arguments.clojure
  (:import (clojure.scala.interop.default.arguments TestClass)))

(defn -main [& _]

  (let [instance (TestClass.)
        default-argument (.sum$default$2 instance)]
    (println default-argument)                      ;prints 10
    (println (.sum instance 30 40))                 ;prints 70
    (println (.sum instance 30 default-argument)))) ;prints 40
