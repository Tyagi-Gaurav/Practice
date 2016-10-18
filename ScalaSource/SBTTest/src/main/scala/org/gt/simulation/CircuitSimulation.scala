package org.gt.simulation

/**
 * Created by gauravt on 04/03/15.
 */
abstract class CircuitSimulation extends BasicCircuitSimulation {
  def halfAdder(a :Wire, b :Wire, sum : Wire, carry : Wire) {
    val d,e = new Wire
    or(a, b, d)
    and(a, b, carry)
    inverter(carry, e)
    and(d, e, sum)
  }
}

