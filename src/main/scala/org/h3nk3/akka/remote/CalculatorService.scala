package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem}
import com.typesafe.config.ConfigFactory

class SimpleCalculatorActor extends Actor {
  def receive = {
    case Add(n1, n2) => 
      println("Calculating %d + %d".format(n1, n2))
      sender ! AddResult(n1, n2, n1 + n2)
    case Subtract(n1, n2) => 
      println("Calculating %d - %d".format(n1, n2))
      sender ! SubtractResult(n1, n2, n1 - n2)
  }
}

class CalculatorService extends Bootable {
  // Load the "service" part of the application.conf file to configure this ActorSystem
  val system = ActorSystem("CalculatorService", ConfigFactory.load.getConfig("service"))
  val actor = system.actorOf(Props[SimpleCalculatorActor], "simpleCalculator")

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

object CalcSrv {
  def main(args: Array[String]) {
    new CalculatorService
    println("Started Calculator Service - waiting for messages")
  }
}
