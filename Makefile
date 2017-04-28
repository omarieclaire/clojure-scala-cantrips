clean:
	rm -rf src/clojure

compile: clean
	cd src && scalac ./*/scala.scala

show-primary-constructor: compile
	javap src/clojure/scala/interop/primary/constructor/TestClass.class

run-primary-constructor: compile
	lein run -m primary-constructor.clojure

show-nary-constructor: compile
	javap src/clojure/scala/interop/nary/constructor/TestClass.class

run-nary-constructor: compile
	lein run -m n-ary-constructor.clojure

show-immutable-fields: compile
	javap src/clojure/scala/interop/immutable/fields/TestClass.class

run-immutable-fields: compile
	lein run -m immutable-fields.clojure

show-mutable-fields: compile
	javap src/clojure/scala/interop/mutable/fields/TestClass.class

run-mutable-fields: compile
	lein run -m mutable-fields.clojure

show-class-methods: compile
	javap src/clojure/scala/interop/class/methods/TestClass.class

run-class-methods: compile
	lein run -m class-methods.clojure

show-companion-object: compile
	javap src/clojure/scala/interop/companion/object/*.class

run-companion-object: compile
	lein run -m companion-object.clojure

show-object-members: compile
	javap src/clojure/scala/interop/object/members/*.class

run-object-members: compile
	lein run -m object-members.clojure

show-apply-method: compile
	javap src/clojure/scala/interop/apply/method/*.class

run-apply-method: compile
	lein run -m apply-method.clojure

show-default-arguments: compile
	javap src/clojure/scala/interop/default/arguments/*.class

run-default-arguments: compile
	lein run -m default-arguments.clojure

show-case-classes: compile
	javap src/clojure/scala/interop/case/classes/*.class

run-case-classes: compile
	lein run -m case-classes.clojure

show-option-classes:
	javap -classpath scala-library-2.12.1.jar scala.Option scala.Option$$ scala.None scala.None$$ scala.Some scala.Some$$

run-option-classes-scala: compile compile
	cd src && scala clojure.scala.interop.option.classes.main

run-option-classes-clojure: compile
	lein run -m option-classes.clojure
