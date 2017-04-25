
trait Actionable {
  type Action = () => Unit

  case class WorkItem(delay : Int, action : Action)

  var currentTime = 0

  var actionList : List[WorkItem] = Nil

  def addAction(action : Action) : Unit = {
    addAction(0, action)
  }

  def addAction(delay : Int, action : Action) : Unit = {
    val workItem = WorkItem(delay, action)
    actionList = workItem :: actionList
    action
  }

  def execute = actionList.foreach(x => {
    currentTime += x.delay
    x action ()
  })
}

class Wire extends Actionable {
  var signal : Boolean = false

  def getSignal = signal

  def setSignal(newSignal : Boolean) = {
    signal = newSignal
    execute
  }
}

object SimulationExecutor extends App {

  def inverter(input : Wire, output : Wire): Unit = {
    def invertAction() = {
      val inputSeg = input.getSignal
      output.setSignal(!input.getSignal)
    }
    input addAction(1, invertAction)
  }

  def andGate(a : Wire, b : Wire, output : Wire): Unit = {
    def andAction() = {
      val aSeg = a.getSignal
      val bSeg = b.getSignal
      output.setSignal(aSeg && bSeg)
    }

    a addAction(1, andAction)
    b addAction(1, andAction)
  }

  def orGate(a : Wire, b : Wire, output : Wire): Unit = {
    def orAction() = {
      val aSeg = a.getSignal
      val bSeg = b.getSignal
      output.setSignal(aSeg || bSeg)
    }

    a addAction(1, orAction)
    b addAction(1, orAction)
  }

  def halfAdder(a : Wire, b: Wire, s : Wire, c: Wire) = {
    val d, e = new Wire
    orGate(a, b, d)
    andGate(a, b, c)
    inverter(c, e)
    andGate(d, e, s)
  }

  var input1, input2, sum, carry = new Wire

  halfAdder(input1, input2, sum, carry)

  System.out.println ("Sum : " + sum.getSignal)
  System.out.println ("Carry: " + carry.getSignal)
  System.out.println ("**************")

  input1 setSignal true

  System.out.println ("Sum : " + sum.getSignal)
  System.out.println ("Carry: " + carry.getSignal)
  System.out.println ("**************")

  input2 setSignal true

  System.out.println ("Sum : " + sum.getSignal)
  System.out.println ("Carry: " + carry.getSignal)
  System.out.println ("**************")
}
