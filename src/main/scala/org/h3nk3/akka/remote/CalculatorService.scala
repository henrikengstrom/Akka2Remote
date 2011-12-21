package org.h3nk3.akka.remote

import akka.kernel.Bootable
import akka.actor.{Props, Actor, ActorSystem}
import java.io.{File => JFile}
import com.typesafe.config.ConfigFactory

class CalcServiceActor extends Actor {
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
  val system = ActorSystem("CalculatorService", ConfigFactory.load.getConfig("service"))
  val actor = system.actorOf(Props[CalcServiceActor], "calcServiceActor")

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

object CalculatorSrv {
  def main(args: Array[String]) {
    new CalculatorService
    println("Started Calculator Service")
  }
}
