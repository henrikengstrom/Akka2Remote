package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem}
import com.typesafe.config.ConfigFactory
import scala.util.Random

class LookupApplication extends Bootable {
  // Load the "remotelookup" part of the application.conf file to configure this ActorSystem
  val system = ActorSystem("LookupApplication", ConfigFactory.load.getConfig("remotelookup"))
  val actor = system.actorOf(Props[LookupActor], "lookupActor")

  def doSomething(op: MathOp) = {
    actor ! op
  }

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

/**
 * This actor looks up a remote actor and sends it a message.
 * The actor also handles results sent back from that remote actor.
 */
class LookupActor extends Actor {
  def receive = {
    case op: MathOp => context.system.actorFor("akka://CalculatorService@127.0.0.1:2553/user/simpleCalculator") ! op
    case result: MathResult => result match {
      case AddResult(n1, n2, r) => println("Add result: %d + %d = %d".format(n1, n2, r))
      case SubtractResult(n1, n2, r) => println("Sub result: %d - %d = %d".format(n1, n2, r))
    }
  }
}

object LookupApp {
  def main(args: Array[String]) {
    val app = new LookupApplication
    println("Started Lookup Application")

    // This loop is used to illustrate the example - it does not illustrate how you would implement a real-world application!
    while (true) {
      if (Random.nextInt(100) % 2 == 0) app.doSomething(Add(Random.nextInt(100), Random.nextInt(100)))
      else app.doSomething(Subtract(Random.nextInt(100), Random.nextInt(100)))

      Thread.sleep(200)    
    }
  }
}
