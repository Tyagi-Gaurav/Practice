package org.gt.simulation

/**
 * Created by gauravt on 03/03/15.
 */
abstract class Simulation {
  /*
  Defines Action to be an alias of a function that
  takes in an empty parameter list and returns a unit.
   */
  type Action = () => Unit

  private var curTime : Int = 0

  def currentTime = curTime

  case class WorkItem(time : Int, action : Action)

  private var agenda : List[WorkItem] = List()

  private def insert(ag: List[WorkItem], item : WorkItem) : List[WorkItem] = {
    if (ag.isEmpty || item.time < ag.head.time) item :: ag
      else
        ag.head :: insert(ag.tail, item)
  }

  /**
   * Second Argument which is a function that takes a block which has a type of
   * () => Unit. which is the same type as Action. Why is this done ? Is it because
   * we don't want it evaluated when the function call is made and it should be
   * called only when we really want to call it. i.e. Pass by Name ??
   *
   * @param delay
   * @param block
   * @return
   */
  def afterDelay(delay:Int)(block : => Unit) = {
    /*
    Second argument of the method below is
     */
    val item = WorkItem(curTime+delay, () => block)
    agenda = insert(agenda, item)
  }

  private def next() =
    (agenda : @unchecked) match {
      case item :: rest => agenda = rest
                           curTime = item.time
                           item.action()
    }

  def run() {
    afterDelay(0) {
      println ("*** Simulation Started, time = " + currentTime + "***")
    }
    while (!agenda.isEmpty) next()
  }
}
