/**
  Copyright [2011] [Henrik Engstroem]

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
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

class CalculatorApplication extends Bootable {
  // Load the "calculator" part of the application.conf file to configure this ActorSystem
  val system = ActorSystem("CalculatorApplication", ConfigFactory.load.getConfig("calculator"))
  val actor = system.actorOf(Props[SimpleCalculatorActor], "simpleCalculator")

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

object CalcApp {
  def main(args: Array[String]) {
    new CalculatorApplication
    println("Started Calculator Application - waiting for messages")
  }
}
