package clojure.scala.interop.`class`.methods

class TestClass{
  def method1: Int = 1
  def method2(): Int = 2
  def inc(x: Int): Int = x + 1
  def sum(x: Int, y:Int): Int = x + y
  def sideEffect(x: Int, y:Int): Unit = {
    println("does a side effect")
  }
}
