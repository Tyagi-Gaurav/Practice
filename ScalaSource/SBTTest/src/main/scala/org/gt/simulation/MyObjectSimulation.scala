package org.gt.simulation

/**
 * Created by gauravt on 04/03/15.
 */
object MyObjectSimulation extends CircuitSimulation {
  override def orGateDelay: Int = 1

  override def andGateDelay: Int = 3

  override def inverterDelay: Int = 5
}
