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

import akka.actor.Actor

trait MathOp

case class Add(nbr1: Int, nbr2: Int) extends MathOp

case class Subtract(nbr1: Int,  nbr2: Int) extends MathOp

case class Multiply(nbr1: Int,  nbr2: Int) extends MathOp

case class Divide(nbr1: Int,  nbr2: Int) extends MathOp

trait MathResult

case class AddResult(nbr: Int, nbr2: Int, result: Int) extends MathResult

case class SubtractResult(nbr1: Int, nbr2: Int, result: Int) extends MathResult

case class MultiplicationResult(nbr1: Int, nbr2: Int, result: Int) extends MathResult

case class DivisionResult(nbr1: Double, nbr2: Int, result: Double) extends MathResult

class AdvancedCalculatorActor extends Actor {
  def receive = {
    case Multiply(n1, n2) => 
      println("Calculating %d * %d".format(n1, n2))
      sender ! MultiplicationResult(n1, n2, n1 * n2)
    case Divide(n1, n2) => 
      println("Calculating %d / %d".format(n1, n2))
      sender ! DivisionResult(n1, n2, n1 / n2)
  }
}