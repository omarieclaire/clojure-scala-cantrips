# Clojure-Scala Cantrips

This document covers couple of tips and tricks on how to consume scala apis from a clojure codebase.

Clojure and scala, both being laguages that run on jvm, have a common denominator. That is the java byte code. In order to use a scala library from clojure code we need to know two things;
  * How does a scala api manifest itselfs in java byte code
  * How to consume a java api from clojure code

The internals of scala to java translation can be uncovered by using the [javap](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javap.html) tool. The knowledge about clojure - java interoperability is drawn from [its documentation](https://clojure.org/reference/java_interop). All of the examples shown in this document are put together based on these two resources.


#### Who is this document for?

This document covers the basic usecases of clojure - scala interoperability. People who want to learn about how the scala structures are represented in java and how the clojure code interacts with them would be interested.

#### Why?

This document is more for educational purposes rather than production usage. There are libraries out there that does a good job in providing the functionality covered here. This document is for understanding the underlying semantics of clojure - scala interoperability.


## Let’s get started

#### Prerequisites

The source code of all of the examples below can be found in the [`src` directory](src). In order to see the java api of the scala structure you can run;
```make
make show-{{example-name}}
```

In order to execute the clojure code that consumes the scala api  you can run;
```make
make run-{{example-name}}
```

## Accessing the constructor

Instantiating regular scala classes is as straightforward as instantiating a java class. Given [this class](src/primary_constructor/scala.scala);
```scala
class TestClass(param1: Int, param2: String)
```

`make show-primary-constructor` generates the java api below ;
```java
public class TestClass {
  public TestClass(int, java.lang.String);
}
```

And the clojure code to instantiate this class looks like [this](src/primary_constructor/clojure.clj);
```clojure
(let [instance1 (new TestClass 1 "test")
      instance2 (TestClass. 1 "test")] ;the shorthand notation

      (println instance1)   ;TestClass@
      (println instance2))) ;TestClass@
```

## Accessing the n-ary constructors

Just same as in Java, having multiple constructor is also possible in Scala. Accessing these constructors is as straightforward as accessing the primary constructor. [The class below](src/n_ary_constructor/scala.scala);
```scala
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
```

Generates the java api below (`make show-nary-constructor`);
```java
public class clojure.scala.interop.nary.constructor.TestClass {
  public int a();
  public int b();
  public clojure.scala.interop.nary.constructor.TestClass(int, int);
  public clojure.scala.interop.nary.constructor.TestClass(int);
  public clojure.scala.interop.nary.constructor.TestClass();
  public clojure.scala.interop.nary.constructor.TestClass(java.lang.String);
}
```

Accessing these constructors are demonstrated [in this class](src/n_ary_constructor/clojure.clj);
```clojure
(let [instance1 (TestClass. 1 2)
    instance2 (TestClass. 2)
    instance3 (TestClass.)
    instance4 (TestClass. "4")]

    (println (.a instance1)) ; 1
    (println (.b instance1)) ; 2

    (println (.a instance2)) ; 2
    (println (.b instance2)) ; 0

    (println (.a instance3)) ; 0
    (println (.b instance3)) ; 0

    (println (.a instance4))   ; 4
    (println (.b instance4)))) ; 4
```

## Accessing the immutable instance fields

Let’s look at [the class below](src/immutable_fields/scala.scala);
```scala
class TestClass(val attr1: Int) {
 val attr2: Int = 2
}
```

This class generates the api below (`make show-immutable-fields`);
```java
public class clojure.scala.interop.immutable.fields.TestClass {
  public int attr1();
  public int attr2();
  public clojure.scala.interop.immutable.fields.TestClass(int);
}
```
From the above code we can deduce that defining a `val` in the constructor or in the class body doesn’t change the java api of the class. Both `attr1` and `attr2` follow the same pattern in their disassembled code. Another noteworthy point is that scala `val`s are turned into java methods.

Let’s try to access these fields. Following the clojure - java interop accessing the methods looks like [this class](src/immutable_fields/clojure.clj);
```clojure
(let [instance (TestClass. 1)
        attr1 (.attr1 instance)
        attr2 (.attr2 instance)]
    (println attr1)   ; 1
    (println attr2))) ; 2
````

## Accessing mutable instance fields

Let’s repeat the same exercise [with mutable fields](src/mutable_fields/scala.scala), namely `var`s;
```scala
class TestClass(var attr1: Int){
 var attr2: Int = 2
}
```

This class compiles into (`make show-mutable-fields`);
```java
public class clojure.scala.interop.mutable.fields.TestClass {
  public int attr1();
  public void attr1_$eq(int);
  public int attr2();
  public void attr2_$eq(int);
  public clojure.scala.interop.mutable.fields.TestClass(int);
}
```

Again here defining a field in the constructor or in the class body doesn’t make a difference on the java api. Accessing the mutable fields is same as accessing the immutable fields. Demonstrated below;
```clojure
(let [instance (TestClass. 1)
        attr1 (.attr1 instance)
        attr2 (.attr2 instance)]
    (println attr1)   ; 1
    (println attr2))  ; 2
```

What about mutating these fields? You probably noticed these weird named methods (we’ll have plenty of these!);
```java
public void attr1_$eq(int);
```

This is a method that takes an `int` and doesn’t return back a value. This is the setter method of the variable `attr1`, that lets us mutate its value. Let's see its usage below;
```clojure
(let [instance (TestClass. 10)]
    (println (.attr1 instance)) ; 10
    (.attr1_$eq instance 99)
    (println (.attr1 instance)))) ; 99
```

## Class methods

Next up let’s have a look at class methods. Our study case is the class below;

*[class-methods.scala](src/class_methods/scala.scala)*
```scala
class TestClass{
  def method1: Int = 1
  def method2(): Int = 2
  def inc(x: Int): Int = x + 1
  def sum(x: Int, y:Int): Int = x + y
  def sideEffect(x: Int, y:Int): Unit = {
    println("does a side effect")
  }
}
```

This class gives us the interface below;

*`make show-class-methods`*
```java
public class clojure.scala.interop.class.methods.TestClass {
  public int method1();
  public int method2();
  public int inc(int);
  public int sum(int, int);
  public void sideEffect(int, int);
  public clojure.scala.interop.class.methods.TestClass();
}
```

`method1` and `method2` are parameterless methods, therefore invoking them is not different than how we access the `val`s. [As we noted above](https://github.com/grandbora/clojure-scala-cantrips#accessing-the-immutable-instance-fields) `val`s are turned into parameterless java methods. For the other methods only difference is that they expect parameters. The code below demonstrates how we invoke these methods;

*[class-methods.clojure](src/class_methods/clojure.clj)*
```clojure
(let [instance (TestClass.)]
  (println (.method1 instance))    ; prints 1
  (println (.method2 instance))    ; prints 2
  (println (.inc instance 2))      ; prints 3
  (println (.sum instance 2, 3))   ; prints 5
  (.sideEffect instance 2, 3)))    ; does a side effect
```

---------------

So far we indulged ourselves with the fundamental concepts that easily map to java structures. Let’s get to the things where scala sets itself apart from java.

## Companion objects

Companion objects are one of the useful tools of scala. Let’s see how they manifest themselves in Java code. The object below;

*[objects.scala](src/objects/scala.scala)*
```scala
object TestClass
```

Yields the java interface below;

*`make show-companion-object`*
```java
public final class clojure.scala.interop.object.TestClass$ {
  public static clojure.scala.interop.object.TestClass$ MODULE$;
  public static {};
}

public final class clojure.scala.interop.object.TestClass {
}
```

We see two classes generated by scala. These are `TestClass` and `TestClass$`. Lets investigate these classes. The `TestClass$` has different characteristics than the classes we have inspected so far; it has static methods.

Scala doesn’t have static methods, its way of providing a similar functionality is through objects. Objects are translated into Java classes, however they are a [singleton classes](https://en.wikipedia.org/wiki/Singleton_pattern) and the way to access their singleton instance is via the static `MODULE$` method. Following the clojure-java interop documentation we can access the object singleton instance as shown below;

*[objects.clojure](src/objects/clojure.clj)* *`make run-objects`*
```clojure
(let [singleton-instance (TestClass$/MODULE$)]
  (println singleton-instance)) ; #object[clojure.scala.interop.objects.TestClass$]
```

#### A note on the companion objects
Before we move on to the following topic, there is an interesting point I'd like to draw attention on.

The companion objects in scala do not exist without their classes. In this example we see this case manifesting itself. Even though the `TestClass` companion object is defined without a class, its java interface yields two classes, `TestClass` and `TestClass$`. Another interesting point is that the generated java class `TestClass` has no constructors, not even a private one.

`javap -private src/clojure/scala/interop/companion/object/TestClass.class` yields the interface below ([-private flag](http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javap.html) is used to show private members);

```java
public final class clojure.scala.interop.objects.TestClass {
}
```

Trying to instantiate the `TestClass` without any arguments yields the error below;
```bash
Caused by: java.lang.IllegalArgumentException: No matching ctor found for class clojure.scala.interop.objects.TestClass
``` 

This shows that even the [java default constructor](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html) is not provided to this class. I don't have knowledge on the reasoning behind this and it is out of the scope of this documentation. *However I am happy to hear if you know more about this.*





TODO:
Mention versions 
mention deps `lein` `scalac`








