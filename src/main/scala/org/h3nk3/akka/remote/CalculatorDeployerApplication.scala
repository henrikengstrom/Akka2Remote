package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem}
import com.typesafe.config.ConfigFactory
import scala.util.Random

class CalculatorDeployerApplication extends Bootable {
  val system = ActorSystem("CalcDeplApp", ConfigFactory.load.getConfig("remoteconfig"))
  val actor = system.actorOf(Props[CalcDeployerAppActor], "calcAppActor")

  val remoteActor = system.actorOf(Props[SpecialCalcServiceActor], "calcServDepl")
  remoteActor ! Multiply(3, 3)
   
  val path = remoteActor.path.toString
 
  def perform(op: MathOp) = {
    actor ! Tuple2(path, op)
  }

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}


class CalcDeployerAppActor extends Actor {
  def receive = {
    case Tuple2(path: String, op: MathOp) =>
      context.actorFor(path) ! op
    case result: MathResult => result match {
      case MultiplicationResult(n1, n2, r) => println("Mul result: %d * %d = %d".format(n1, n2, r))
      case DivisionResult(n1, n2, r) => println("Div result: %d / %d = %d".format(n1, n2, r))
    }
  }
}

object CalculatorDeployerApp {
  def main(args: Array[String]) {
    val app = new CalculatorDeployerApplication
    println("Started Calculator Deployer Application")
    while (true) {
      if (Random.nextInt(100) % 2 == 0) app.perform(Multiply(Random.nextInt(100), Random.nextInt(100)))
      else app.perform(Divide(Random.nextInt(100), Random.nextInt(100)))
    
      Thread.sleep(200)    
    }
  }
}
