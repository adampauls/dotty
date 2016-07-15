object overloaded {

  def f(x: String): String = x
  def f[T >: Null](x: T): Int = 1

  val x1 = f("abc")
  val x2 = f(new Integer(1))
  val x3 = f(null)

  val x4: String => String = f
  val x5: String => Any = f
  val x6: Any = f _

  def g(): Int = 1
  def g(x: Int): Int = 2

  val y1: Int => Int = g
  val y2: Any = g _

  println(g)

  val xs = List("a", "b")
  xs.mkString

  def map(f: Char => Char): String = ???
  def map[U](f: Char => U): Seq[U] = ???
  val r1 = map(x => x.toUpper)
  val t1: String = r1
  val r2 = map(x => x.toInt)
  val t2: Seq[Int] = r2

  def flatMap(f: Char => String): String = ???
  def flatMap[U](f: Char => Seq[U]): Seq[U] = ???
  val r3 = flatMap(x => x.toString)
  val t3: String = r3
  val r4 = flatMap(x => List(x))
  val t4: Seq[Char] = r4

  def bar(f: (Char, Char) => Unit): Unit = ???
  def bar(f: Char => Unit) = ???
  bar((x, y) => ())
  bar (x => ())

  def combine(f: (Char, Int) => Int): Int = ???
  def combine(f: (String, Int) => String): String = ???
  val r5 = combine((x: Char, y) => x + y)
  val t5: Int = r5
  val r6 = combine((x: String, y) => x ++ y.toString)
  val t6: String = r6

  // test result disambiguation
  trait A
  trait B
  class C extends A with B
  def fr(x: A): A = x
  def fr(x: B): B = x
  val a: A = fr(new C)
  val b: B = fr(new C)
}

// from #1381

object Foo {
  class Bar[T]
  implicit def const[T](x: T): Bar[T] = ???

  def bar[T](e: T): Any = ???
  def bar[T](e: Bar[T]): Any = ???

  val b: Bar[Int] = ???
  bar(b)
}

object Test2 {
    trait A; trait B
    class C1 {
       def f(x: A): Unit = println("A")
    }
    class C2 extends C1 {
       def f(x: B): Unit = println("B")
    }
    object Test extends C2 with App {
      implicit def a2b(x: A): B = new B {}
      f(new A {})
    }
}
