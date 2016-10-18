/**
 * Created by gauravt on 26/01/15.
 */


abstract class aQueue[T] {
  def isEmpty : Boolean

  def enqueue(x : T) : Unit

  def dequeue : Option[T]

  def peek : Option[T]
}

class NEQueue[T] extends aQueue[T] {
  var xs : List[T] = Nil

  def isEmpty = xs.size == 0

  def enqueue(x: T) : Unit = xs = xs ::: List(x)

  def dequeue : Option[T] = xs match {
    case List() => None
    case y :: ys =>
        xs = ys
        Some(y)
  }

  def peek  = if (!isEmpty) Some(xs.head) else None
}
