package clojure.scala.interop.apply.method

class TestClass(a: Int, b: Int)

object TestClass {
  def apply(x: Int): TestClass = new TestClass(x, x)
}
