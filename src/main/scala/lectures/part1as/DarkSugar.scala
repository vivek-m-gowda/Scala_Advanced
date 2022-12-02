package lectures.part1as

object DarkSugar extends App {

  //syntax sugar 1: methods with single parm
  def singleAgrMethod(arg: Int): String = s"$arg little ducks"

  //we can call singleAgrMethod in java style along with some code bock
  val description = singleAgrMethod(42)
  //or
  val descriptionV2 = singleAgrMethod {
    // any code
    println("hi")
    43
  }
  println(description)
  println(descriptionV2)

  //syntax sugar 2: single abstract method
  trait Action {
    def act(x: Int): Int
  }

  val anInstance: Action = new Action {
    override def act(x: Int): Int = x + 1
  }

  val aFunkyInstance: Action = (x: Int) => x + 1 // magic

  // example: Runnables
  //thread creation
  val aThread = new Thread(new Runnable {
    override def run(): Unit = println("hello, Scala")
  })

  //runnable in scala
  val aSweeterThread = new Thread(() => println("sweet, Scala!"))


  // example: Runnables
  abstract class AnAbstractType {
    def implemented: Int = 23

    def f(a: Int): Unit
  }
  //runnable in scala'
  val anAbstractInstance: AnAbstractType = (a: Int) => println("sweet")

  // syntax sugar #3: the :: and #:: methods are special

  val prependedList = 2 :: List(3, 4)
  // 2.::(List(3,4))
  // List(3,4).::(2)

  // scala spec: last char decides associativity of method
  1 :: 2 :: 3 :: List(4, 5)
  List(4, 5).::(3).::(2).::(1) // equivalent

  class MyStream[T] {
    def -->:(value: T): MyStream[T] = this // actual implementation here
  }

  val myStream = 1 -->: 2 -->: 3 -->: new MyStream[Int]

  // syntax sugar #4: multi-word method naming

  class TeenGirl(name: String) {
    def `and then said`(gossip: String) = println(s"$name said $gossip")
  }

  val lilly = new TeenGirl("Lilly")
  lilly `and then said` "Scala is so sweet!"

  // syntax sugar #5: infix types
  class Composite[A, B]

  val composite: Int Composite String = ??? //infix generic types

  class -->[A, B]

  val towards: Int --> String = ???

  // syntax sugar #6: update() is very special, much like apply()
  val anArray = Array(1, 2, 3)
  anArray(2) = 7 // rewritten to anArray.update(2, 7)
  // used in mutable collections
  // remember apply() AND update()!

  // syntax sugar #7: setters for mutable containers
  class Mutable {
    private var internalMember: Int = 0 // private for OO encapsulation

    def member = internalMember // "getter"

    def member_=(value: Int): Unit =
      internalMember = value // "setter"
  }

  val aMutableContainer = new Mutable
  aMutableContainer.member = 42 // rewrittern as aMutableContainer.member_=(42)

}
