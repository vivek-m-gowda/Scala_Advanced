
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

ADVANCED FUNCTIONAL PROGRAMMING

3.1	Partial Functions
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	When a function is not able to produce a return for every single variable input data given to it then that function is termed as Partial function. 
		It can determine an output for a subset of some practicable inputs only. It can only be applied partially to the stated inputs.

>>	Some important points:

			* Partial functions are beneficent in understanding many inconsistent kind of Scala functions.
			* It can be interpreted by utilizing case statements.
			* It is a Trait, which needs two methods namely isDefinedAt and apply to be implemented.
			
>>	Partial Function can only have one parameter type

			val aFunction = (x: Int) => x + 1 //Function1[Int, Int] === Int => Int

			val aFussyFunction = (x: Int) =>
				if (x == 1) 42
				else if (x == 2) 56
				else if (x == 5) 999
				else throw new FunctionNotApplicableException

			class FunctionNotApplicableException extends RuntimeException

			val aNicerFussyFunction = (x: Int) => x match {
				case 1 => 42
				case 2 => 56
				case 5 => 999
			}
			//  {1,2,5} => Int

			val aPartialFunction: PartialFunction[Int, Int] = {
				case 1 => 42
				case 2 => 56
				case 5 => 999
			} // partial function value

			println(aPartialFunction(2))
			//  println(aNicerFussyFunction(9))
			//  println(aPartialFunction(57273))
			
>>	PF utilities	
			println(aPartialFunction.isDefinedAt(67))

>>	To inpuct the value to console use
			
			scala.io.Source.stdin.getLines().map(chatbot).foreach(println)
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3.2	Functional Collections: A functional Set || Enhancing A Functional Set
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	A great strength of Scala collections is that they come with dozens of methods out of the box, 
		and those methods are consistently available across the immutable and mutable collections types. 
		
>>	The benefits of this are that you no longer need to write custom for loops every time you need to work with a collection, 
		and when you move from one project to another, you’ll find these same methods used, rather than more custom for loops.

			val a = List(10, 20, 30, 40, 10)      // List(10, 20, 30, 40, 10)

			a.distinct                            // List(10, 20, 30, 40)
			a.drop(2)                             // List(30, 40, 10)
			a.dropRight(2)                        // List(10, 20, 30)
			a.head                                // 10
			a.headOption                          // Some(10)
			a.init                                // List(10, 20, 30, 40)
			a.intersect(List(19,20,21))           // List(20)
			a.last                                // 10
			a.lastOption                          // Some(10)
			a.slice(2,4)                          // List(30, 40)
			a.tail                                // List(20, 30, 40, 10)
			a.take(3)                             // List(10, 20, 30)
			a.takeRight(2)                        // List(40, 10)

>>	EXERCISE 
			- removing an element
			- intersection with another set
			- difference with another set
			
>>	Refer MySet file
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3.3	Currying and Partially Applied Functions
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Currying in Scala is simply a technique or a process of transforming a function. 
		This function takes multiple arguments into a function that takes single argument. 
		It is applied widely in multiple functional languages.
		
>>	curried functions
			val superAdder: Int => Int => Int =
			x => y => x + y

			val add3 = superAdder(3) // Int => Int = y => 3 + y
			println(add3(5))
			println(superAdder(3)(5)) // curried function

>>	curried method
			def curriedAdder(x: Int)(y: Int): Int = x + y 

			val add4: Int => Int = curriedAdder(4)

>>	EXERCISES

			1.  Process a list of numbers and return their string representations with different formats
				 Use the %4.2f, %8.6f and %14.12f with a curried formatter function.

					def curriedFormatter(s: String)(number: Double): String = s.format(number)
					val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)
					val simpleFormat = curriedFormatter("%4.2f") _ // lift
					val seriousFormat = curriedFormatter("%8.6f") _
					val preciseFormat = curriedFormatter("%14.12f") _
					println(numbers.map(curriedFormatter("%14.12f"))) // compiler does sweet eta-expansion for us
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3.4	Lazy Evaluation
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Lazy evaluation or call-by-need is a evaluation strategy where an expression isn’t evaluated until its first use i.e to postpone the evaluation till its demanded. 
		Functional programming languages like Haskell use this strategy extensively. 
		C, C++ are called strict languages who evaluate the expression as soon as it’s declared. 
		Then there are languages like Scala who are strict by default but can be lazy if specified explicitly i.e. of mixed type. 
		
			// lazy DELAYS the evaluation of values
					lazy val x: Int = {
					println("hello")
					42
				}
				println(x)
				println(x)

>>	Exercise: implement a lazily evaluated, singly linked STREAM of elements.
		naturals = MyStream.from(1)(x => x + 1) = stream of natural numbers (potentially infinite!)
		naturals.take(100).foreach(println) // lazily evaluated stream of the first 100 naturals (finite stream)
		naturals.foreach(println) // will crash - infinite!
		naturals.map(_ * 2) // stream of all even numbers (potentially infinite)

			abstract class MyStream[+A] {
				def isEmpty: Boolean
				def head: A
				def tail: MyStream[A]
				def #::[B >: A](element: B): MyStream[B] // prepend operator
				def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams
				def foreach(f: A => Unit): Unit
				def map[B](f: A => B): MyStream[B]
				def flatMap[B](f: A => MyStream[B]): MyStream[B]
				def filter(predicate: A => Boolean): MyStream[A]
				def take(n: Int): MyStream[A] // takes the first n elements out of this stream
				def takeAsList(n: Int): List[A]
			}

			object MyStream {
				def from[A](start: A)(generator: A => A): MyStream[A] = ???
			}
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

3.5	Monads
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Monads is a construction which performs successive calculations. It is an object which covers the other object. 
		It is worth noting that here, the output of an operation at some step is an input to another computations, which is a parent to the recent step of the program stated.
		
>>	Monads are nothing more than a mechanism to sequence computations around values augmented with some additional feature. 

>>	The three monad laws are:

			Left identity
			Right identity
			Associativity

>>	 Left Identity
		The first of the three laws, called “left identity”, says that applying a function f using the flatMap function to a 
		value x lifted by the unit function is equivalent to applying the function f directly to the value x:

			Monad.unit(x).flatMap(f) = f(x)

		If we take our Lazy monad, we have to prove that the following holds:

			Lazy(x).flatMap(f) == f(x)

		Hence, we can substitute flatMap(f) with f(x), so the property holds by definition.

>>	 Right Identity
		The second monadic law is called “right identity”. It states that application of the flatMap function using the unit function as the function f results in the original monadic value:

			x.flatMap(y => Monad.unit(y)) = x

		It’s easy to prove that our Lazy monad also fulfills this law:

			Lazy(x).flatMap(y => Lazy(y)) == Lazy(x)

		As we can substitute the term flatMap(y => Lazy(y)) with the result of the application, Lazy(x), the property holds by definition.
		
>>	Associativity
		The last of the three monadic laws is the hardest to deal with and is called “associativity”. 
		This law says that applying two functions f and g to a monad value using a sequence of flatMap calls is equivalent to 
		applying g to the result of the application of the flatMap function using f as the parameter:

			x.flatMap(f).flatMap(g) = o.flatMap(x => f(x).flapMap(g))

		Hence, for the Lazy monad, the above rule becomes:

			Lazy(x).flatMap(f).flatMap(g) == f(x).flatMap(g)

		If we apply the substitution derived from the”left identity” law to the terms Lazy(x).flatMap(f) of the right side of the equation, we obtain exactly f(x).flatMap(g). 
		So, the associativity rule also holds for our monad

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

FUNCTIONAL CONCURRENT PROGRAMMING

4.1	Intro to Parallel Programming on the JVM
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Parallel computing is a type of computation where different computations can be performed at the same time.

>>	Basic principle: The problem can be divided into subproblems, each of which can be solved simultaneously.

>>	Parallel computation:

			Optimal use of parallel hardware to execute computation quickly
			Division into subproblems
			Mainly concerns about: Speed
			Mainly used for: Algorithmic problems, numeric computation, Big Data processing
			
>>	Concurrent programming:

			May or may not offer multiple execution starts at the same time
			Mainly concerns about: Convenience, better responsiveness, maintainability

			val runnable = new Runnable {
				override def run(): Unit = println("Running in parallel")
			}
			val aThread = new Thread(runnable)

			aThread.start() // gives the  signal to the JVM to start a JVM thread
			// create a JVM thread => OS thread
			runnable.run() // doesn't do anything in parallel!
			aThread.join() // blocks until aThread finishes running

-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

4.2	JVM Thread Communication [lock, deadlock, livelock]
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	  /*
				the producer-consumer problem
				producer -> [ ? ] -> consumer
			*/
					class SimpleContainer {
						private var value: Int = 0

						def isEmpty: Boolean = value == 0
						def set(newValue: Int) = value = newValue
						def get = {
							val result = value
							value = 0
							result
						}
					}

					def naiveProdCons(): Unit = {
						val container = new SimpleContainer

						val consumer = new Thread(() => {
							println("[consumer] waiting...")
							while (container.isEmpty) {
								println("[consumer] actively waiting...")
					}

					println("[consumer] I have consumed " + container.get)
					})

					val producer = new Thread(() => {
						println("[producer] computing...")
						Thread.sleep(500)
						val value = 42
						println("[producer] I have produced, after long work, the value " + value)
						container.set(value)
					})

					consumer.start()
					producer.start()
				}
				naiveProdCons()

>>	wait and notify

			def smartProdCons(): Unit = {
				val container = new SimpleContainer

				val consumer = new Thread(() => {
					println("[consumer] waiting...")
					container.synchronized {
						container.wait()
				}

			// container must have some value
			println("[consumer] I have consumed " + container.get)
			})

			val producer = new Thread(() => {
				println("[producer] Hard at work...")
				Thread.sleep(2000)
				val value = 42

			container.synchronized {
				println("[producer] I'm producing " + value)
				container.set(value)
				container.notify()
				}
			})

			consumer.start()
			producer.start()
			}
			smartProdCons()  
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

4.3	Futures and Promises
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	The Future and Promise are two high-level asynchronous constructs in concurrent programming.

>>	The Future is a read-only placeholder for the result of ongoing computation. 
		It acts as a proxy for an actual value that doesn’t yet exist. 
		Think of IO-Bound or CPU-Bound operations, which take a notable time to complete.
		
				  def calculateMeaningOfLife: Int = {
					Thread.sleep(2000)
					42
				  }

				  val aFuture = Future {
					calculateMeaningOfLife // calculates the  meaning of  life on ANOTHER thread
				  } // (global) which is passed by the compiler


				  println(aFuture.value) // Option[Try[Int]]

				  println("Waiting on the future")
				  aFuture.onComplete {
					case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
					case Failure(e) => println(s"I have failed with $e")
				  } // SOME thread

				  Thread.sleep(3000)
				  
>>	Promise

		The Promise is a writable, single-assignment container that completes a Future. The Promise is similar to the Future. However, 
		the Future is about the read-side of an asynchronous operation, while the Promise is about the write-side.

		In other words, the Promise is a write handle to a value that will be available at some point in the future. 
		It allows us to put the value of a completed asynchronous operation into the Future, and change the state of the Future from not completed to completed by invoking the success method. Once the Promise is completed, we cannot call the success() method again.

		Now, let’s look at the steps needed to create a Future from a Promise:

		Create a Promise for a type that we want to return in the future.
		Run the block of the computation code in an ExecutionContext by calling the execute method on the ExecutionContext. When the execution succeeds, 
		we call the success() method of Promise to set the value of the completed computation. If the computation fails, we call the failure() method to change the state of Promise to failure.
		Return the Future, which will contain the value of our Promise.