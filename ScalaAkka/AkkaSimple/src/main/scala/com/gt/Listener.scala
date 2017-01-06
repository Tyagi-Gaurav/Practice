package com.gt

import akka.actor._

class Listener extends Actor {
  def receive = {
    case PiApproximation(pi, duration) =>
      println ("\n\tPi Approximation: \t\t%s\n\tCalculation Time: \t%s"
      .format(pi, duration))
      context.system.shutdown()
  }
}
