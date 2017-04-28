package clojure.scala.interop.nary.constructors

class TestClass(val a: Int, val b: Int) {

  def this(a: Int) {
    this(a, 0);
  }

  def this() {
    this(0, 0);
  }

  def this(a: String) {
    this(a.toInt, a.toInt);
  }
}
