package clojure.scala.interop.option.classes

object main extends App {

  val a = Option(null)
  val b = Option(2)

  println(a) // None
  println(b) // Some(2)

  println(a.isEmpty) // true
  println(b.isEmpty) // false

  // println(a.get) // NoSuchElementException("None.get")
  println(b.get) // 2

  b match {
    case None => println("Nope")
    case Some(x) => println(x.toString) // "2"
  }
}
