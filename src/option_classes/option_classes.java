public abstract class scala.Option<A> implements scala.Product, scala.Serializable {
  public static final long serialVersionUID;
  public static <A> scala.Option<A> empty();
  public static <A> scala.Option<A> apply(A);
  public static <A> scala.collection.Iterable<A> option2Iterable(scala.Option<A>);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public java.lang.String productPrefix();
  public abstract boolean isEmpty();
  public boolean isDefined();
  public abstract A get();
  public final <B> B getOrElse(scala.Function0<B>);
  public final <A1> A1 orNull(scala.Predef$$less$colon$less<scala.runtime.Null$, A1>);
  public final <B> scala.Option<B> map(scala.Function1<A, B>);
  public final <B> B fold(scala.Function0<B>, scala.Function1<A, B>);
  public final <B> scala.Option<B> flatMap(scala.Function1<A, scala.Option<B>>);
  public <B> scala.Option<B> flatten(scala.Predef$$less$colon$less<A, scala.Option<B>>);
  public final scala.Option<A> filter(scala.Function1<A, java.lang.Object>);
  public final scala.Option<A> filterNot(scala.Function1<A, java.lang.Object>);
  public final boolean nonEmpty();
  public final scala.Option<A>.WithFilter withFilter(scala.Function1<A, java.lang.Object>);
  public final <A1> boolean contains(A1);
  public final boolean exists(scala.Function1<A, java.lang.Object>);
  public final boolean forall(scala.Function1<A, java.lang.Object>);
  public final <U> void foreach(scala.Function1<A, U>);
  public final <B> scala.Option<B> collect(scala.PartialFunction<A, B>);
  public final <B> scala.Option<B> orElse(scala.Function0<scala.Option<B>>);
  public scala.collection.Iterator<A> iterator();
  public scala.collection.immutable.List<A> toList();
  public final <X> scala.util.Either<X, A> toRight(scala.Function0<X>);
  public final <X> scala.util.Either<A, X> toLeft(scala.Function0<X>);
  public static final java.lang.Object $anonfun$orNull$1(scala.Predef$$less$colon$less);
  public scala.Option();
}

public final class scala.Option$ implements scala.Serializable {
  public static scala.Option$ MODULE$;
  public static {};
  public <A> scala.collection.Iterable<A> option2Iterable(scala.Option<A>);
  public <A> scala.Option<A> apply(A);
  public <A> scala.Option<A> empty();
}

public final class scala.None {
  public static java.lang.String toString();
  public static int hashCode();
  public static boolean canEqual(java.lang.Object);
  public static scala.collection.Iterator<java.lang.Object> productIterator();
  public static java.lang.Object productElement(int);
  public static int productArity();
  public static java.lang.String productPrefix();
  public static scala.runtime.Nothing$ get();
  public static boolean isEmpty();
  public static <X> scala.util.Either<scala.runtime.Nothing$, X> toLeft(scala.Function0<X>);
  public static <X> scala.util.Either<X, scala.runtime.Nothing$> toRight(scala.Function0<X>);
  public static scala.collection.immutable.List<scala.runtime.Nothing$> toList();
  public static scala.collection.Iterator<scala.runtime.Nothing$> iterator();
  public static <B> scala.Option<B> orElse(scala.Function0<scala.Option<B>>);
  public static <B> scala.Option<B> collect(scala.PartialFunction<scala.runtime.Nothing$, B>);
  public static <U> void foreach(scala.Function1<scala.runtime.Nothing$, U>);
  public static boolean forall(scala.Function1<scala.runtime.Nothing$, java.lang.Object>);
  public static boolean exists(scala.Function1<scala.runtime.Nothing$, java.lang.Object>);
  public static <A1> boolean contains(A1);
  public static scala.Option<scala.runtime.Nothing$>.WithFilter withFilter(scala.Function1<scala.runtime.Nothing$, java.lang.Object>);
  public static boolean nonEmpty();
  public static scala.Option<scala.runtime.Nothing$> filterNot(scala.Function1<scala.runtime.Nothing$, java.lang.Object>);
  public static scala.Option<scala.runtime.Nothing$> filter(scala.Function1<scala.runtime.Nothing$, java.lang.Object>);
  public static <B> scala.Option<B> flatten(scala.Predef$$less$colon$less<scala.runtime.Nothing$, scala.Option<B>>);
  public static <B> scala.Option<B> flatMap(scala.Function1<scala.runtime.Nothing$, scala.Option<B>>);
  public static <B> B fold(scala.Function0<B>, scala.Function1<scala.runtime.Nothing$, B>);
  public static <B> scala.Option<B> map(scala.Function1<scala.runtime.Nothing$, B>);
  public static <A1> A1 orNull(scala.Predef$$less$colon$less<scala.runtime.Null$, A1>);
  public static <B> B getOrElse(scala.Function0<B>);
  public static boolean isDefined();
}

public final class scala.None$ extends scala.Option<scala.runtime.Nothing$> {
  public static scala.None$ MODULE$;
  public static final long serialVersionUID;
  public static {};
  public boolean isEmpty();
  public scala.runtime.Nothing$ get();
  public java.lang.String productPrefix();
  public int productArity();
  public java.lang.Object productElement(int);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public boolean canEqual(java.lang.Object);
  public int hashCode();
  public java.lang.String toString();
  public java.lang.Object get();
}

public final class scala.Some<A> extends scala.Option<A> {
  public static final long serialVersionUID;
  public static <A> scala.Option<A> unapply(scala.Some<A>);
  public static <A> scala.Some<A> apply(A);
  public A value();
  public boolean isEmpty();
  public A get();
  public A x();
  public <A> scala.Some<A> copy(A);
  public <A> A copy$default$1();
  public java.lang.String productPrefix();
  public int productArity();
  public java.lang.Object productElement(int);
  public scala.collection.Iterator<java.lang.Object> productIterator();
  public boolean canEqual(java.lang.Object);
  public int hashCode();
  public java.lang.String toString();
  public boolean equals(java.lang.Object);
  public scala.Some(A);
}

public final class scala.Some$ implements scala.Serializable {
  public static scala.Some$ MODULE$;
  public static {};
  public final java.lang.String toString();
  public <A> scala.Some<A> apply(A);
  public <A> scala.Option<A> unapply(scala.Some<A>);
}
