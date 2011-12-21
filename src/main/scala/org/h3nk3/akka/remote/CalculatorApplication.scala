package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem}
import com.typesafe.config.ConfigFactory
import scala.util.Random

class CalculatorApplication extends Bootable {
  val system = ActorSystem("CalcApp", ConfigFactory.load.getConfig("application"))
  val actor = system.actorOf(Props[CalcAppActor], "calcAppActor")

  def perform(op: MathOp) = {
    actor ! op
  }

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}


class CalcAppActor extends Actor {
  def receive = {
    case op: MathOp =>
      context.system.actorFor("akka://CalculatorService@127.0.0.1:2553/user/calcServiceActor") ! op
    case result: MathResult => result match {
      case AddResult(n1, n2, r) => println("Add result: %d + %d = %d".format(n1, n2, r))
      case SubtractResult(n1, n2, r) => println("Sub result: %d - %d = %d".format(n1, n2, r))
    }
  }
}

object CalculatorApp {
  def main(args: Array[String]) {
    val app = new CalculatorApplication
    println("Started Calculator Application")
    while (true) {
      if (Random.nextInt(100) % 2 == 0) app.perform(Add(Random.nextInt(100), Random.nextInt(100)))
      else app.perform(Subtract(Random.nextInt(100), Random.nextInt(100)))
    
      Thread.sleep(200)    
    }
  }
}
