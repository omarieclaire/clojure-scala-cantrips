(ns option-classes.clojure
  (:import (scala Option)))

(defn -main [& _]

  (let [a (Option/apply nil)
        b (Option/apply 2)]

  (println a) ; None
  (println b) ; Some(2)

  (println (.isEmpty a)) ; true
  (println (.isEmpty b)) ; false

  ; (println (.get a)) ; java.util.NoSuchElementException: None.get
  (println (.get b)) ; 2

  (if (.isEmpty b)
    (println "Nope")
    (println (.toString (.get b)))))) ; 2
