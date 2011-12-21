package org.h3nk3.akka.remote

import akka.actor.{Props, Actor, ActorSystem}

trait MathOp

case class Add(nbr1: Int, nbr2: Int) extends MathOp

case class Subtract(nbr1: Int,  nbr2: Int) extends MathOp

case class Multiply(nbr1: Int,  nbr2: Int) extends MathOp

case class Divide(nbr1: Int,  nbr2: Int) extends MathOp

trait MathResult

case class AddResult(nbr: Int, nbr2: Int, result: Int) extends MathResult

case class SubtractResult(nbr1: Int, nbr2: Int, result: Int) extends MathResult

case class MultiplicationResult(nbr1: Int, nbr2: Int, result: Int) extends MathResult

case class DivisionResult(nbr1: Int, nbr2: Int, result: Int) extends MathResult

class SpecialCalcServiceActor extends Actor {
  def receive = {
    case Multiply(n1, n2) => 
      println("Calculating %d * %d".format(n1, n2))
      sender ! MultiplicationResult(n1, n2, n1 + n2)
    case Divide(n1, n2) => 
      println("Calculating %d / %d".format(n1, n2))
      sender ! DivisionResult(n1, n2, n1 / n2)
  }
}