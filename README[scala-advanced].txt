
-----------------------------------------------------ADVANCED SCALA AND FUNCTIONAL PROGRAMMING | ROCK THE JVM-------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A TEST OF ADVANCED SCALA

2.1	Recap: The Scala Basics
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	RECAP

		*  how to declare val and variable
		*  how to write expression
		* instruction v/s expression
		* Unit type [which do not written anything but only do side effect]
		* functions
		* recursion:  stack and tail
		* OOP
		* abstract classes and traits
		* method notations
		* many operators are rewritten as methods
		* anonymous classes
		*  generics
		*  singletons and companions
		*  case classes
		*  exceptions and try/catch/finally
		*  packaging and imports
		*  functional programming
		*  map, flatMap, filter
		*  for-comprehension
		*  Scala collections: Seqs, Arrays, Lists, Vectors, Maps, Tuples
		*  "collections": Options, Try
		*  pattern matching
		*  all the patterns
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

2.2	Dark Syntax Sugar
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Syntax sugar 1: methods with single parm
>>	we can call singleAgrMethod in java style along with some code bock

			def singleAgrMethod(arg: Int): String = s"$arg little ducks"
			val description = singleAgrMethod(42)
			//or
			val descriptionV2 = singleAgrMethod{
				//any code
				println("hi")
				43
			}
			println(description)
			println(descriptionV2)

>>	syntax sugar 2: single abstract method

			trait Action {
				def act(x: Int): Int
			}

			val anInstance: Action = new Action {
				override def act(x: Int): Int = x + 1
			}
			val aFunkyInstance: Action = (x: Int) => x + 1 // magic

>>	example: Runnables
			* thread creation
				val aThread = new Thread(new Runnable {
					override def run(): Unit = println("hello, Scala")
				})

			* runnable in scala
				val aSweeterThread = new Thread(() => println("sweet, Scala!"))

>>	syntax sugar #3: the :: and #:: methods are special

			val prependedList = 2 :: List(3, 4)
			// 2.::(List(3,4))
			// List(3,4).::(2)

			// scala spec: last char decides associativity of method
			1 :: 2 :: 3 :: List(4, 5)
			List(4, 5).::(3).::(2).::(1) // equivalent

>>	syntax sugar #4: multi-word method naming

			class TeenGirl(name: String) {
				def `and then said`(gossip: String) = println(s"$name said $gossip")
			}
			val lilly = new TeenGirl("Lilly")
			lilly `and then said` "Scala is so sweet!"

>>	syntax sugar #5: infix types
			
			class Composite[A, B]
			//infix generic types
			val composite: Int Composite String = ???
			class -->[A, B]
			val towards: Int --> String = ???

>>	syntax sugar #6: update() is very special, much like apply()

			val anArray = Array(1, 2, 3)
			anArray(2) = 7 // rewritten to anArray.update(2, 7)
		* used in mutable collections
		* remember apply() AND update()!

>>	syntax sugar #7: setters for mutable containers

		class Mutable {
		private var internalMember: Int = 0 // private for OO encapsulation
		def member = internalMember // "getter"
		def member_=(value: Int): Unit =
		  internalMember = value // "setter"
		}

		val aMutableContainer = new Mutable
		aMutableContainer.member = 42 // rewrittern as aMutableContainer.member_=(42)
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

2.3	Advanced Pattern Matching
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Pattern Matching is compactable with
			- constants
			- wildcards
			- case classes
			- tuples
			- some special magic like above

			val numbers = List(1)
			val description = numbers match {
				case head :: Nil => println(s"the only element is $head.")
				case _ =>
			}
			
>>	

			class Person(val name: String, val age: Int)
			object Person {
				def unapply(person: Person): Option[(String, Int)] =
				  if (person.age < 21) None
				  else Some((person.name, person.age))

				def unapply(age: Int): Option[String] =
				Some(if (age < 21) "minor" else "major")
			}
			
			val bob = new Person("Bob", 25)
			val greeting = bob match {
			case Person(n, a) => s"Hi, my name is $n and I am $a yo."
			}
			println(greeting)

			val legalStatus = bob.age match {
			case Person(status) => s"My legal status is $status"
			}
			println(legalStatus)

>>	 nfix patterns
			case class Or[A, B](a: A, b: B)
				val either = Or(2, "two")
				val humanDescription = either match {
				case number Or string => s"$number is written as $string"
			}
			println(humanDescription)
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

A TEST OF ADVANCED SCALA

2.1	Recap: The Scala Basics
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------











