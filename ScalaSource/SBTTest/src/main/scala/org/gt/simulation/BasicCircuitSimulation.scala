package org.gt.simulation

/**
 * Created by gauravt on 04/03/15.
 */
abstract class BasicCircuitSimulation extends Simulation {
  def inverterDelay : Int
  def andGateDelay : Int
  def orGateDelay : Int

  class Wire {
    private var signalVal = false
    private var actions : List[Action] = List()

    def getSignal = signalVal

    def setSignal(s : Boolean) =
        if (s != signalVal) {
          signalVal = s
          /*
          This is a shorthand for f=> f()..i.e given a function f,
           apply an empty list of parameters to f.
           */
          actions foreach(_ ())
        }

    def addAction(a : Action) = {
      actions = a :: actions
      a()
    }
  }

  def inverter(input: Wire, output: Wire) = {
    def invertAction() = {
      val inputSig = input.getSignal
      afterDelay(inverterDelay) {
        output setSignal !inputSig
      }
    }

    input addAction invertAction
  }

  /**
   * The effect of the andGate method is to add andAction to both of its
   * input wires a1 and a2. This action, when invoked, gets both input
   * signals and installs another action that sets the output signal to
   * the conjunction of both input signals. This other action is to be
   * executed after AndGateDelay units of simulated time.
   *
   * Note that the output has to be recomputed if either of the input
   * wires changes. That’s why the same andAction is installed on each of
   * the two input wires a1 and a2.
   *
   * @param i1
   * @param i2
   * @param o
   */
  def and(i1: Wire, i2: Wire, o : Wire) = {
    def andAction() = {
      var a1Sig = i1.getSignal
      var a2Sig = i2.getSignal
      afterDelay(andGateDelay) {
        o setSignal (a1Sig && a2Sig)
      }
    }
    i1 addAction andAction
    i2 addAction andAction
  }

  def or(i1: Wire, i2: Wire, o : Wire) = {
    def orAction() = {
      var a1Sig = i1.getSignal
      var a2Sig = i2.getSignal
      afterDelay(orGateDelay) {
        o setSignal (a1Sig || a2Sig)
      }
    }
    i1 addAction orAction
    i2 addAction orAction
  }

  def probe(name : String, w : Wire) {
    def probeAction() {
      println (name + " " + currentTime + "-" + w.getSignal)
    }

    w addAction probeAction
  }
}
