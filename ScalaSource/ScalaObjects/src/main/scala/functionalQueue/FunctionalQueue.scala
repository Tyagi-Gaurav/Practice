package functionalQueue

class FunctionalQueue[+T](private val leading : List[T], private val trailing : List[T]) {

  def head = mirror.leading.head

  def tail = {
    val q = mirror
    new FunctionalQueue(q.leading.tail, q.trailing)
  }

  def mirror = if (leading.isEmpty) new FunctionalQueue(trailing.reverse, Nil) else this

  def enqueue[U >: T](x : U) = new FunctionalQueue[U](leading, x :: trailing)
}

object FunctionalQueue {
  def apply[T](xs : T*) = new FunctionalQueue[T](xs.toList, Nil)
}