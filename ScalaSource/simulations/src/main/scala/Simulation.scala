
type Action = () => Unit

class Wire {
  var currentSignal = false

  var actions : List[Action] = List.empty

  def getSignal = currentSignal

  def setSignal(sigVal : Boolean) = {
    if (sigVal != currentSignal) {
      currentSignal = sigVal
      actions.foreach(_ ())
    }
  }

  def addAction(action : Action) = {
    actions += action
    action()
  }
}

abstract class Simulation(inverterDelay: Int, andDelay : Int, orDelay : Int) {
  var currentTime = 0

  def inverter(input : Wire, output: Wire) : Action = {
    def invertAction() = {
      afterDelay(inverterDelay) { output setSignal !input.getSignal }
    }

    input addAction invertAction
  }
}