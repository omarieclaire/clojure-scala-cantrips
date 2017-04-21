package clojure.scala.interop.default.arguments

class TestClass {
  def sum(a: Int, b: Int = 10): Int = a + b
}