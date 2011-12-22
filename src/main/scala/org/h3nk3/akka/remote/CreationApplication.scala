package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem, ActorPath}
import com.typesafe.config.ConfigFactory
import scala.util.Random

/**
 * This class serves as an example of how to deploy an actor on a remote node.
 * <br>
 * In this example we want to reuse the "CalculatorService" ActorSystem already created,
 * but as this only contains the SimpleCalculatorActor per default and we want to do some advanced
 * calculation we need to deploy the AdvancedCalculatorActor to that ActorSystem before we start to
 * do the number crunching.
 */
class CreationApplication extends Bootable {
  // Load the "remotecreation" part of the application.conf file to configure this ActorSystem
  val system = ActorSystem("RemoteCreation", ConfigFactory.load.getConfig("remotecreation"))
  val localActor = system.actorOf(Props[CreationActor], "creationActor")

  // This is where the magic sauce is applied.
  // The "advancedCalculator" references the configuration file.
  val remoteActor = system.actorOf(Props[AdvancedCalculatorActor], "advancedCalculator")

  // Get the path to the remote actor as we want to send messages to it.
  val path = remoteActor.path

  def doSomething(op: MathOp) = {
    localActor ! Tuple2(path, op)
  }

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

class CreationActor extends Actor {
  def receive = {
    case Tuple2(path: ActorPath, op: MathOp) => context.actorFor(path) ! op
    case result: MathResult => result match {
      case MultiplicationResult(n1, n2, r) => println("Mul result: %d * %d = %d".format(n1, n2, r))
      case DivisionResult(n1, n2, r) => println("Div result: %.0f / %d = %.2f".format(n1, n2, r))
    }
  }
}

object CreationApp {
  def main(args: Array[String]) {
    val app = new CreationApplication
    println("Started Creation Application")

    // This loop is used to illustrate the example - it does not illustrate how you would implement a real-world application!
    while (true) {
      if (Random.nextInt(100) % 2 == 0) app.doSomething(Multiply(Random.nextInt(20), Random.nextInt(20)))
      else app.doSomething(Divide(Random.nextInt(10000), (Random.nextInt(99) + 1)))

      Thread.sleep(200)    
    }
  }
}
