package com.gt

import scala.concurrent.duration._

sealed trait PiMessage
//Sent to Master to start calculation
case object Calculate extends PiMessage

//Sent from master to Worker actors
case class Work(start: Int, nrOfElements : Int) extends PiMessage

//Sent from Worker actors to Master containing the results of calculation
case class Result(value : Double) extends PiMessage

//Sent from Master to Listener containing final Pi result.
case class PiApproximation(pi: Double, duration: Duration) extends PiMessage
