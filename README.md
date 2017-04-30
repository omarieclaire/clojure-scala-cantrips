# Clojure-Scala Cantrips

This document covers couple of tips and tricks on how to consume Scala apis from Clojure codebases.

### Index
  * [Intro](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#intro---lets-get-started)
  * [Prerequisites](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#prerequisites)
  * [The primary constructor](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#the-primary-constructor)
  * [N-ary constructors](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#n-ary-constructors)
  * [Immutable instance fields](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#immutable-instance-fields)
  * [Mutable instance fields](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#mutable-instance-fields)
  * [Class methods](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#class-methods)
  * [Companion objects](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#companion-objects)
  * [Class members of companion objects](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#class-members-of-companion-objects)
  * [The magic apply method](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#the-magic-apply-method)
  * [Default arguments](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#default-arguments)
  * [The option class](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#the-option-class)
  * [Outro](https://github.com/grandbora/clojure-scala-cantrips/blob/master/README.md#outro)

## Intro - *let’s get started*

Clojure and Scala, both being laguages that run on JVM, have a common denominator. That is the Java bytecode. In order to use a Scala library from a Clojure code we need to know two things;
  * What kind of a Java interface does this Scala code produce?
  * How do we consume a given Java interface in a Clojure code?

The first question can be answered by utilizing the [javap](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javap.html) tool. This tool prints out the interface of the given Java class files. The knowledge on Clojure - Java interoperability, which is necessary to answer the second question, is documented at [clojure.org](https://clojure.org/reference/java_interop). All of the examples shown in this document are put together based on these two resources.

#### Who is this document for?

This document would be interesting for people wo want to have practical information on Scala to Java conversion and Clojure - Java interop.

#### Why?

This document is created for educational purposes. The examples here can be used on production but I'd highly encourage checking out the existing Scala - Clojure interop libraries. There are open source libraries out there that does a good job in providing the functionality covered here. This document is for understanding the underlying semantics of Clojure - Scala interoperability.

## Prerequisites

The source code of all of the examples can be found in the [`src` directory](src). In order to see the Java interface produced by the Scala code of an example you can run;
```make
make show-{{example-name}}
```

In order to execute the Clojure code that uses this Java interface you can run;
```make
make run-{{example-name}}
```

Running the examples requires Java, the Scala compiler (`scalac`) and the Clojure build tool Leiningen (`lein`) to be installed on the host machine. The examples are tested in the versions of these tools shown below;
```
Scala compiler 2.12.1
Leiningen 2.7.1
Java 1.8.0_92
```

## The primary constructor

Instantiating a regular Scala class is as straightforward as instantiating a Java class. The class below;

*[primary-constructor.scala](src/primary_constructor/scala.scala)*
```scala
class TestClass(param1: Int, param2: String)
```

generates the Java interface below;

*`make show-primary-constructor`*
```java
public class clojure.scala.interop.primary.constructor.TestClass {
  public clojure.scala.interop.primary.constructor.TestClass(int, java.lang.String);
}
```

And the Clojure code to instantiate this class looks like below;

*[primary-constructor.clj](src/primary_constructor/clojure.clj)* *`make run-primary-constructor`*
```clojure
(let [instance1 (new TestClass 1 "test")
      instance2 (TestClass. 1 "test")] ;the shorthand notation

      (println instance1)   ; prints TestClass@...
      (println instance2))) ; prints TestClass@...
```

## N-ary constructors

Just same as in Java, having multiple constructor is also possible in Scala. Accessing these constructors is as straightforward as accessing the primary constructor. The class below;

*[nary-constructors.scala](src/n_ary_constructors/scala.scala)*
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

generates the Java interface below;

*`make show-nary-constructors`*
```java
public class clojure.scala.interop.nary.constructors.TestClass {
  public int a();
  public int b();
  public clojure.scala.interop.nary.constructor.TestClass(int, int);
  public clojure.scala.interop.nary.constructor.TestClass(int);
  public clojure.scala.interop.nary.constructor.TestClass();
  public clojure.scala.interop.nary.constructor.TestClass(java.lang.String);
}
```

Accessing these constructors are demonstrated below;

*[nary-constructors.clj](src/n_ary_constructors/clojure.clj)* *`make run-nary-constructors`*
```clojure
(let [instance1 (TestClass. 1 2)
    instance2 (TestClass. 2)
    instance3 (TestClass.)
    instance4 (TestClass. "4")]

    (println (.a instance1)) ; prints 1
    (println (.b instance1)) ; prints 2

    (println (.a instance2)) ; prints 2
    (println (.b instance2)) ; prints 0

    (println (.a instance3)) ; prints 0
    (println (.b instance3)) ; prints 0

    (println (.a instance4))   ; prints 4
    (println (.b instance4)))) ; prints 4
```

## Immutable instance fields

Let’s have a look at the class below;

*[immutable-fields.scala](src/immutable_fields/scala.scala)*
```scala
class TestClass(val attr1: Int) {
 val attr2: Int = 2
}
```

This class produces the interface below;

*`make show-immutable-fields`*
```java
public class clojure.scala.interop.immutable.fields.TestClass {
  public int attr1();
  public int attr2();
  public clojure.scala.interop.immutable.fields.TestClass(int);
}
```

From the above code we can deduce that defining a `val` either in the constructor or in the class body doesn’t change the signature of the generated Java method. Both `attr1` and `attr2` have the same signature. Another noteworthy point is that Scala `val`s are turned into Java methods.

Let’s try to access these fields. Following the Clojure - Java interop accessing the methods looks like below;

*[immutable-fields.clj](src/immutable_fields/clojure.clj)* *`make run-immutable-fields`*
```clojure
(let [instance (TestClass. 1)
        attr1 (.attr1 instance)
        attr2 (.attr2 instance)]
    (println attr1)   ; prints 1
    (println attr2))) ; prints 2
````

## Mutable instance fields

Let’s repeat the same exercise with mutable fields, namely `var`s;

*[mutable-fields.scala](src/mutable_fields/scala.scala)*
```scala
class TestClass(var attr1: Int){
 var attr2: Int = 2
}
```

This class compiles into;

*`make show-mutable-fields`*
```java
public class clojure.scala.interop.mutable.fields.TestClass {
  public int attr1();
  public void attr1_$eq(int);
  public int attr2();
  public void attr2_$eq(int);
  public clojure.scala.interop.mutable.fields.TestClass(int);
}
```

Again here defining a field either in the constructor or in the class body doesn't change the signature of the resulting method. Accessing the mutable fields is done in the same way as in accessing the immutable fields. Demonstrated below;

*[mutable-fields.clj](src/mutable_fields/clojure.clj)* *`make run-mutable-fields`*
```clojure
(let [instance (TestClass. 1)
        attr1 (.attr1 instance)
        attr2 (.attr2 instance)]
    (println attr1)   ; prints 1
    (println attr2))  ; prints 2
```

What about mutating these fields? You probably noticed these weird named methods (we’ll have plenty of these!);
```java
public void attr1_$eq(int);
```

This is a method that takes an `int` and doesn’t return back a value. This is the setter method of the variable `attr1`, that lets us mutate its value. Let's see its usage below;

*[mutable-fields.clj](src/mutable_fields/clojure.clj)* *`make run-mutable-fields`*
```clojure
(let [instance (TestClass. 10)]
    (println (.attr1 instance)) ; prints 10
    (.attr1_$eq instance 99)
    (println (.attr1 instance)))) ; prints 99
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

`method1` and `method2` are parameterless methods, therefore invoking them is not different than how we access the `val`s. [As we noted above](https://github.com/grandbora/clojure-scala-cantrips#accessing-the-immutable-instance-fields) `val`s are turned into parameterless Java methods. For the other methods only difference is that they expect parameters. The code below demonstrates how we invoke these methods;

*[class-methods.clj](src/class_methods/clojure.clj)* *`make run-class-methods`*
```clojure
(let [instance (TestClass.)]
  (println (.method1 instance))    ; prints 1
  (println (.method2 instance))    ; prints 2
  (println (.inc instance 2))      ; prints 3
  (println (.sum instance 2 3))   ; prints 5
  (.sideEffect instance 2 3)))    ; does a side effect
```

## Companion objects

So far we indulged ourselves with the fundamental concepts that easily map to Java structures. Let’s get to the things where Scala sets itself apart from Java.

Companion objects are one of the useful tools of Scala. Let’s see how they manifest themselves in Java code. The object below;

*[companion_objects.scala](src/companion_objects/scala.scala)*
```scala
object TestClass
```

Yields the Java interface below;

*`make show-companion-object`*
```java
public final class clojure.scala.interop.object.TestClass$ {
  public static clojure.scala.interop.object.TestClass$ MODULE$;
  public static {};
}

public final class clojure.scala.interop.object.TestClass {
}
```

We see two classes generated by Scala. These are `TestClass` and `TestClass$`. Lets investigate these classes. The `TestClass$` has different characteristics than the classes we have inspected so far; it has static methods.

Scala doesn’t have static methods, its way of providing a similar functionality is through objects. Objects are translated into Java classes, however they are a [singleton classes](https://en.wikipedia.org/wiki/Singleton_pattern) and the way to access their singleton instance is via the static class variable `MODULE$`. Following the Clojure - Java interop documentation we can access the object singleton instance as shown below;

*[companion_objects.clj](src/companion_objects/clojure.clj)* *`make run-companion-object`*
```clojure
(let [singleton-instance (TestClass$/MODULE$)]
  (println singleton-instance)) ; prints #object[clojure.scala.interop.objects.TestClass$]
```

#### A note on the companion objects
Before we move on to the following topic, there is an interesting point I'd like to draw attention on.

**The companion objects in Scala do not exist without their classes.** In this example we see this case manifesting itself. Even though the `TestClass` object is defined without a class, its Java interface yields two classes, `TestClass` and `TestClass$`. Another interesting point is that the generated Java class `TestClass` has no constructors, not even a private one.

`javap -private src/clojure/scala/interop/companion/object/TestClass.class` yields the interface below ([-private flag](http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/javap.html) is used to show private members);

```java
public final class clojure.scala.interop.objects.TestClass {
}
```

Trying to instantiate the `TestClass` without any arguments yields the error below;
```bash
Caused by: java.lang.IllegalArgumentException: No matching ctor found for class clojure.scala.interop.objects.TestClass
``` 

This shows that even the [default Java constructor](https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html) is not provided to this class. I don't have knowledge on the reasoning behind this and it is out of the scope of this documentation. *However I am happy to hear if you know more about this.*

## Class members of companion objects

Now that we know how to access the instance of an object, let’s investigate how we can run operations on the Java interface it provides. Let’s consider the companion object below;

*[object-members.scala](src/object_members/scala.scala)*
```scala
object TestClass {
  val immutableField = 0
  var mutableField = 1
  def inc(x: Int): Int = x + 1
}
```

The object above yields the Java interface below;

*`make show-object-members`*
```java
public final class clojure.scala.interop.object.members.TestClass$ {
  public static clojure.scala.interop.object.members.TestClass$ MODULE$;
  public static {};
  public int immutableField();
  public int mutableField();
  public void mutableField_$eq(int);
  public int inc(int);
}

public final class clojure.scala.interop.object.members.TestClass {
  public static int inc(int);
  public static void mutableField_$eq(int);
  public static int mutableField();
  public static int immutableField();
}
```

Looking at the interface above, there are two ways of accessing the provided functionality; either using the static methods on `TestClass` or using the instance methods on `TestClass$`. As far as I tested either way results in identical outcomes. Even the mutable state is shared between these two classes, which would be the expectation of a consumer who is oblivious to the internals of Scala. This is demonstrated below;

*[object-members.clj](src/object_members/clojure.clj)* *`make run-object-members`*
```clojure
(let [object-instance (TestClass$/MODULE$)]

  (println (TestClass/inc 1))        ; prints 2
  (println (.inc object-instance 1)) ; prints 2

  (println (TestClass/immutableField))        ; prints 0
  (println (.immutableField object-instance)) ; prints 0

  (println (TestClass/mutableField))          ; prints 1
  (println (.mutableField object-instance))   ; prints 1

  (TestClass/mutableField_$eq 3)              ; sets the mutable value to 3 through static method
  (println (TestClass/mutableField))          ; prints 3
  (println (.mutableField object-instance))   ; prints 3

  (.mutableField_$eq object-instance 4)       ; sets the mutable value to 4 through object-instance method
  (println (TestClass/mutableField))          ; prints 4
  (println (.mutableField object-instance))   ; prints 4
```

## The magic apply method

One common pattern in Scala is defining apply method(s) on the companion object. Most of the time these methods are used for constructing an instance of the class, the object they belong is accompanying. Let’s inspect an example of this pattern;

*[apply-method.scala](src/apply_method/scala.scala)*
```scala
class TestClass(a: Int, b: Int)

object TestClass {
  def apply(x: Int): TestClass = new TestClass(x, x)
}
```

The companion object above yields the Java interface below;

*`make show-apply-method`*
```java
public final class clojure.scala.interop.apply.method.TestClass$ {
  public static clojure.scala.interop.apply.method.TestClass$ MODULE$;
  public static {};
  public clojure.scala.interop.apply.method.TestClass apply(int);
}

public class clojure.scala.interop.apply.method.TestClass {
  public static clojure.scala.interop.apply.method.TestClass apply(int);
  public clojure.scala.interop.apply.method.TestClass(int, int);
}
```

While Scala provides a sugar syntax for invoking the apply method, for a consumer written in cojure, apply method is just another static method defined on the class. Invocation of it is demonstrated below;

*[apply-method.clj](src/apply_method/clojure.clj)* *`make run-apply-method`*
```clojure
(let [instance-via-apply (TestClass/apply 4)]
  (println instance-via-apply))) ; prints object[clojure.scala.interop.apply.method.TestClass
```

## Default arguments

Another feature that is characteristic to Scala is [default parameter values](http://docs.scala-lang.org/tutorials/tour/default-parameter-values.html). Given the `sum` method of the class below;

*[default-arguments.scala](src/default_arguments/scala.scala)*
```scala
class TestClass {
  def sum(a: Int, b: Int = 10): Int = a + b
}
```

We observe that it yields the Java interface below;

*`make show-default-arguments`*
```java
public class clojure.scala.interop.default.arguments.TestClass {
  public int sum(int, int);
  public int sum$default$2();
  public clojure.scala.interop.default.arguments.TestClass();
}
```

Naturally the Java signature of the `sum` method doesn’t give any insight about the default parameter value we are providing. However there is a parameterless method curiously named `sum$default$2`. Let’s invoke it;

*[default-arguments.clj](src/default_arguments/clojure.clj)* *`make run-default-arguments`*
```clojure
(let [instance (TestClass.)
      default-argument (.sum$default$2 instance)]
  (println default-argument)                      ; prints 10
  (println (.sum instance 30 40))                 ; prints 70
  (println (.sum instance 30 default-argument)))) ; prints 40
```

As demonstrated above the Clojure way of accessing the default value of a parameter is by calling its generated method. This is probably not more convenient than using a hardcoded parameter, however we need to keep in mind that the default values that are provided by a library may change over time.

## Case classes

[Case classes](http://docs.scala-lang.org/tutorials/tour/case-classes.html) are another handy tool that is commonly used in Scala codebases. Scala provides a bunch of features to case classes for free. These include but not limited to; default apply and unapply methods, immutability, hashCode and equals implementations. Let’s inspect the Java interface of a case class to get the full list;

*[case-classes.scala](src/case_classes/scala.scala)*
```scala
case class TestClass(a: Int, b: Int)
```

The case class above yields the Java interface below;

*`make show-case-classes`*
```java
public final class clojure.scala.interop.case.classes.TestClass$ extends scala.runtime.AbstractFunction2<java.lang.Object, java.lang.Object, clojure.scala.interop.case.classes.TestClass> implements scala.Serializable {
  public static clojure.scala.interop.case.classes.TestClass$ MODULE$;
  public static {};
  public final java.lang.String toString();
  public clojure.scala.interop.case.classes.TestClass apply(int, int);
  public scala.Option<scala.Tuple2<java.lang.Object, java.lang.Object>> unapply(clojure.scala.interop.case.classes.TestClass);
  public java.lang.Object apply(java.lang.Object, java.lang.Object);
}

public class clojure.scala.interop.case.classes.TestClass implements scala.Product,scala.Serializable {
  public static scala.Option<scala.Tuple2<java.lang.Object, java.lang.Object>> unapply(clojure.scala.interop.case.classes.TestClass);
  public static clojure.scala.interop.case.classes.TestClass apply(int, int);
  public static scala.Function1<scala.Tuple2<java.lang.Object, java.lang.Object>, clojure.scala.interop.case.classes.TestClass> tupled();
  public static scala.Function1<java.lang.Object, scala.Function1<java.lang.Object, clojure.scala.interop.case.classes.TestClass>> curried();
  public int a();
  public int b();
  public clojure.scala.interop.case.classes.TestClass copy(int, int);
  public int copy$default$1();
  public int copy$default$2();
  public java.lang.String productPrefix();
  public int productArity();
  public java.lang.Object productElement(int);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public boolean canEqual(java.lang.Object);
  public int hashCode();
  public java.lang.String toString();
  public boolean equals(java.lang.Object);
  public clojure.scala.interop.case.classes.TestClass(int, int);
}
```

For the sake of brevity, this documentation will focus on the parts that are interesting for a Clojure consumer. The interface a case class provides, mostly contains sugar methods that works well in a Scala codebase. Naturally in a Clojure codebase this interface does not bring many benefits. Some examples to these are;
* `apply`: automatically invoked in Scala but not in Clojure. In Clojure code calling the apply method is not any simpler than calling the constructor or any other method, therefore doesn’t bring any benefits.
* `unapply`: useful for pattern matching in Scala, not applicable to Clojure.
* `copy` : handy when the default parameter values are supported, doesn’t bring any benefits in Clojure.

Let’s have a look at the code below to see what parts of this interface we can make use of from Clojure code;

*[case-classes.clj](src/case_classes/clojure.clj)* *`make run-case-classes`*
```clojure
(let [instance (TestClass. 1 2)
      another-instance (TestClass/apply 1 2)]

  (println (.a instance)) ; prints 1
  (println (.b instance)) ; prints 2

  (println (.toString instance))         ; prints TestClass(1,2)
  (println (.toString another-instance)) ; prints TestClass(1,2)

  (println (str instance)) ; prints TestClass(1,2)

  (println (.equals instance another-instance)) ; prints true
  (println (= instance another-instance))))     ; prints true
```

The code above demonstrates the usage of `toString` and `equals` methods. The interface a case class yields has few methods that can be leveraged in a Clojure consumer. However the greater benefit of a case class is being an immutable [data transfer object](https://martinfowler.com/eaaCatalog/dataTransferObject.html)(*obligatory martin fowler link!*). And we can benefit from this in the Clojure codebases.

## The option class

So far we applied the Clojure - Scala interop techniques to the classes that we have defined. Now let’s use our newly acquired powers on a class that comes with the Scala standard library.

The `Option` class is one of the commonly used data structures that is provided by Scala standard library. When we look at the [source code of the `Option` class](https://github.com/scala/scala/blob/v2.12.1/src/library/scala/Option.scala#L11) we see that there are four main structures that holds it together. These are (simplified);
* object Option
* sealed abstract class Option
* final case class Some // extends Option abstract class
* case object None // extends Option abstract class

Its complete Java interface can be seen [here](src/option_classes/option_classes.java) (or `make show-option-classes`)

Let’s have a look at an example usage of the `Option` class in Scala;

*[option-usage.scala](src/option_classes/scala.scala)* *`make run-option-classes-scala`*
```scala
val a = Option(null)
val b = Option(2)

println(a) // prints None
println(b) // prints Some(2)

println(a.isEmpty) // prints true
println(b.isEmpty) // prints false

// println(a.get) // throws NoSuchElementException("None.get")
println(b.get) // prints 2

b match {
  case None => println("Nope")
  case Some(x) => println(x.toString) // prints "2"
}
```

Now that we know how to use the methods of the option class, we can write this code in Clojure. Demonstrated below;

*[option-usage.clj](src/option_classes/clojure.clj)* *`make run-option-classes-clojure`*
```clojure
(let [a (Option/apply nil)
      b (Option/apply 2)]

(println a) ; None
(println b) ; Some(2)

(println (.isEmpty a)) ; prints true
(println (.isEmpty b)) ; prints false

; (println (.get a)) ; throws java.util.NoSuchElementException: None.get
(println (.get b)) ; prints 2

(if (.isEmpty b)
  (println "Nope")
  (println (.toString (.get b)))))) ; prints 2
```

## Outro

The examples that are covered in this document are the most basic use cases. There are plenty of use cases that are not covered here, however this document lays out a methodical approach to figure out how to consume any Scala api.

Hope this document was helpful to you.

*Feel free to send a pr for the corrections and/or additions you want to see.*

![](https://raw.githubusercontent.com/grandbora/clojure-scala-cantrips/master/that'sallfolks.gif)

*Thanks to [@richardatsoundcloud](https://github.com/richardatsoundcloud), [@folone](https://github.com/folone) and [@kevincairo](https://github.com/kevincairo) for their feedback.*
