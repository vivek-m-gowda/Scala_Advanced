
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
		
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

IMPLICITS

5.1	Enter Implicits
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Implicit parameters are the parameters that are passed to a function with implicit keyword in Scala, 
		which means the values will be taken from the context in which they are called. 
		In simpler terms, if no value or parameter is passed to a method or function, then the compiler will look for implicit value and pass it further as the parameter. 

>>	For example, changing an integer variable to a string variable can be done by a Scala compiler rather than calling it explicitly. 
		When implicit keyword used in the parameter scope of the function, all the parameters are marked as implicit.

			  case class Person(name: String) {
				def greet = s"Hi, my name is $name!"
			  }

			  implicit def fromStringToPerson(str: String): Person = Person(str)
			  println("Peter".greet) // println(fromStringToPerson("Peter").greet)
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

5.2	Type Class
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	A type class is a group of types that satisfy a contract typically defined by a trait. 
		They enable us to make a function more ad-hoc polymorphic without touching its code. 
		This flexibility is the biggest win with the type-class pattern.

		A type class is a type system construct that supports ad hoc polymorphism. 
		This is achieved by adding constraints to type variables in parametrically polymorphic types. 
		Such a constraint typically involves a type class T and a type variable a, 
		and means that a can only be instantiated to a type whose members support the overloaded operations associated with T

			  trait HTMLSerializer[T] {
				def serialize(value: T): String
			  }

			  implicit object UserSerializer extends HTMLSerializer[User] {
				def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
			  }

			  val john = User("John", 32, "john@rockthejvm.com")
			  println(UserSerializer.serialize(john))


>>	type enrichment = pimping
>>	compiler doesn't do multiple implicit searches.

		  implicit class RichInt(val value: Int) extends AnyVal {
			def isEven: Boolean = value % 2 == 0

			def sqrt: Double = Math.sqrt(value)
		  new RichInt(42).sqrt

		  42.isEven // new RichInt(42).isEven
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

MASTERING THE TYPE SYSTEM

6.1	Advanced Inheritance
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Scala is a unique language in that it’s statically typed, but often feels flexible and dynamic. 
		For instance, thanks to type inference you can write code like this without explicitly specifying the variable types:
			val a = 1
			val b = 2.0
			val c = "Hi!"
				
>>	Benefits of types
		Statically-typed programming languages offer a number of benefits, including:

		*	Helping to provide strong IDE support
		*	Eliminating many classes of potential errors at compile time
		*	Assisting in refactoring
		*	Providing strong documentation that cannot be outdated since it is type checked

		  // convenience
		  trait Writer[T] {
			def write(value: T): Unit
		  }

		  trait Closeable {
			def close(status: Int): Unit
		  }

		  trait GenericStream[T] {
			// some methods
			def foreach(f: T => Unit): Unit
		  }

		  def processStream[T](stream: GenericStream[T] with Writer[T] with Closeable): Unit = {
			stream.foreach(println)
			stream.close(0)
		  }

>>	Variance
		Variance lets you control how type parameters behave with regards to subtyping. 
		Scala supports variance annotations of type parameters of generic classes, to allow them to be covariant, contravariant, or invariant if no annotations are used. 
		The use of variance in the type system allows us to make intuitive connections between complex types.

		class Foo[+A] // A covariant class
		class Bar[-A] // A contravariant class
		class Baz[A]  // An invariant class
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

6.2	Type Members
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	A member of a class or trait is said to be abstract if that particular member does not have a complete definition in the class. 
		These Abstract members are always implemented in any sub-classes of the class under which it is defined. 
		These kinds of declaration are allowed in many programming languages and is one of the key feature of object oriented programming languages. Scala also allows to declare such methods as shown in the example below:

		abstract class Sample{
		  def contents: Array[String]
		  def width: Int = a
		  def height: Int = b
		}
		Thus in the above class Sample we have declared three methods: contents, width and height. 
		The implementation of the last two methods is already defined whereas in the first method, contents, does not have any kind of implementation mentioned.
		
		  class Animal
		  class Dog extends Animal
		  class Cat extends Animal

		  class AnimalCollection {
			type AnimalType // abstract type member
			type BoundedAnimal <: Animal
			type SuperBoundedAnimal >: Dog <: Animal
			type AnimalC = Cat
		  }

		  val ac = new AnimalCollection
		  val dog: ac.AnimalType = ???

		  //  val cat: ac.BoundedAnimal = new Cat 
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

6.3	Path Dependent Types
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	A dependent type is a type whose definition depends on a value. 
		Suppose we can write a List that has a constraint on the size of the List, for example, NonEmptyList. Also, 
		assume another type like AtLeastTwoMemberList.

>>	A path-dependent type is a specific kind of dependent type where the dependent-upon value is a path. 
		Scala has a notion of a type dependent on a value. This dependency is not expressed in the type signature but rather in the type placement.

		  class Outer {
			class Inner
			object InnerObject
			type InnerType
			def print(i: Inner) = println(i)
			def printGeneral(i: Outer#Inner) = println(i)
		  }

		  def aMethod: Int = {
			class HelperClass
			type HelperType = String
			2
		  }
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

6.4	Self Types
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	Self-types are a way to declare that a trait must be mixed into another trait, even though it doesn’t directly extend it. 
		That makes the members of the dependency available without imports.

>>	A self-type is a way to narrow the type of this or another identifier that aliases this. 
		The syntax looks like normal function syntax but means something entirely different.

>>	To use a self-type in a trait, write an identifier, the type of another trait to mix in, and a => (e.g. someIdentifier: SomeOtherTrait =>).

		trait User:
		  def username: String

		trait Tweeter:
		  this: User =>  // reassign this
		  def tweet(tweetText: String) = println(s"$username: $tweetText")

		class VerifiedTweeter(val username_ : String) extends Tweeter, User:  // We mixin User because Tweeter required it
		  def username = s"real $username_"

		val realBeyoncé = VerifiedTweeter("Beyoncé")
		realBeyoncé.tweet("Just spilled my glass of lemonade")  // prints "real Beyoncé: Just spilled my glass of lemonade"
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

6.5	F bounded polymorphism
-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

>>	














