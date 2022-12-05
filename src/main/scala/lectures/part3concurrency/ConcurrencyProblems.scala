package lectures.part3concurrency

object ConcurrencyProblems extends App {

  def runInParallel(): Unit = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

  // for (_ <- 1 to 10000) runInParallel
  // race condition

  class BankAccount(var amount: Int)

  def buy(bankAccount: BankAccount, thing: String, price: Int) = {
    bankAccount.amount -= price // account.amount = account.amount - price
    //    println("I've bought " + thing)
    //    println("my account is now " + account)
  }

  def demoBankingProblem(): Unit = {
    (1 to 10000).foreach{ _ =>
      val account = new BankAccount(50000)
      val thread1 = new Thread(() => buy(account, "shoes", 3000))
      val thread2 = new Thread(() => buy(account, "iPhone12", 4000))

      thread1.start()
      thread2.start()
      thread1.join()
      thread2.join()
      //Thread.sleep(10)
      if (account.amount != 43000) println(s"AHA! I've just broken the bank: ${account.amount}")
  //    println()
    }
  }

  /*
    thread1 (shoes): 50000
      - account = 50000 - 3000 = 47000
    thread2 (iphone): 50000
      - account = 50000 - 4000 = 46000 overwrites the memory of account.amount
   */

  // option #1: use synchronized()
  def buySafe(bankAccount: BankAccount, thing: String, price: Int) =
    bankAccount.synchronized {
      // no two threads can evaluate this at the same time
      bankAccount.amount -= price
      println("I've bought " + thing)
      println("my account is now " + bankAccount)
    }

  // option #2: use @volatile

  /**
   * Exercises
   *
   * 1) Construct 50 "inception" threads
   * Thread1 -> thread2 -> thread3 -> ...
   * println("hello from thread #3")
   * in REVERSE ORDER
   *
   */
  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $i")
  })

  inceptionThreads(50).start()

  /*
    2 what's the max/min value o x
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  /*
    1) what is the biggest value possible for x? 100
    2) what is the SMALLEST value possible for x? 1
    thread1: x = 0
    thread2: x = 0
      ...
    thread100: x = 0
    for all threads: x = 1 and write it back to x
   */
  threads.foreach(_.join())
  println(x)

  /*
    3 sleep fallacy
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Scala sucks"
  awesomeThread.start()
  Thread.sleep(1001)
  awesomeThread.join() // wait for the awesome thread to join
  println(message)
  /*
    what's the value of message? almost always "Scala is awesome"
    is it guaranteed? NO!
    why? why not?
    (main thread)
      message = "Scala sucks"
      awesomeThread.start()
      sleep() - relieves execution
    (awesome thread)
      sleep() - relieves execution
    (OS gives the CPU to some important thread - takes CPU for more than 2 seconds)
    (OS gives the CPU back  to the MAIN thread)
      println("Scala sucks")
    (OS gives the CPU to awesomethread)
      message = "Scala is awesome"
   */

  // how do we fix this?
  // syncrhonizing does NOT work


}
