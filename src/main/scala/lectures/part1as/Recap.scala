package lectures.part1as

import scala.annotation.tailrec

object Recap extends App {

  // how to declare val and variable
  val aCondition: Boolean = true

  // how to write expression
  val aConditionVal = if (aCondition) 45 else 65
  println(aConditionVal)

  //instruction v/s expression

  val aCodeBolck = {
    if (aCondition) 54  //useless expression because it override by 64
    64
  }

  //Unit type [which do not written anything but only do side effect]
  val theUnit = println("hello scala")

  //functions
  def aFunction (x: Int): Int = x+1


  //recursion:  stack and tail
  @tailrec
  def factorial(n: Int, accumulator: Int): Int =
    if(n <= 0) accumulator
    else factorial(n - 1, n * accumulator)
  //println(factorial(5, 0))

  //OOP
  class Animal
  class Dog extends Animal
  val aDog: Animal = new Animal //subtyping polymorphism

  //abstract classes and traits
  trait Carnivore {
    def eat(a: Animal): Unit
  }

  class Crocodile extends Animal with Carnivore {
    override def eat(a: Animal): Unit = println("crunch")
  }

  //method notations
  val aCroc = new Crocodile
  aCroc.eat(aDog) //.eat notations
  aCroc eat aDog //this is similar to aCroc.eat(aDog) //natural language

  //many operators are rewritten as methods
  //1+2 = 1.+(2)

  //anonymous classes
  val aCarnivore = new Carnivore {
    override def eat(a: Animal): Unit = println("roar!")
  }

  // generics
  abstract class MyList[+A] // variance and variance problems in THIS course

  // singletons and companions
  object MyList

  // case classes
  case class Person(name: String, age: Int)

  // exceptions and try/catch/finally

//  val throwsException = throw new RuntimeException // Nothing
//  val aPotentialFailure = try {
//    throw new RuntimeException
//  } catch {
//    case e: Exception => "I caught an exception"
//  } finally {
//    println("some logs")
//  }

  // packaging and imports

  // functional programming
  val incrementer = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 + 1
  }

  incrementer(1)

  val anonymousIncrementer = (x: Int) => x + 1
  List(1, 2, 3).map(anonymousIncrementer) // HOF
  // map, flatMap, filter

  // for-comprehension
  val pairs = for {
    num <- List(1, 2, 3) // if condition
    char <- List('a', 'b', 'c')
  } yield num + "-" + char

  // Scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
  val aMap = Map(
    "Daniel" -> 789,
    "Jess" -> 555
  )

  // "collections": Options, Try
  val anOption = Some(2)

  // pattern matching
  val x = 2
  val order = x match {
    case 1 => "first"
    case 2 => "second"
    case 3 => "third"
    case _ => x + "th"
  }

  val bob = Person("Bob", 22)
  val greeting = bob match {
    case Person(n, _) => s"Hi, my name is $n"
  }

  // all the patterns


}
