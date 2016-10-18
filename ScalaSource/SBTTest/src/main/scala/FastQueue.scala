/**
 * Created by gauravt on 29/01/15.
 */
trait FastQueue[T] {
  def head : T

  def tail : FastQueue[T]

  def enqueue(x : T) : FastQueue[T]
}

/*
Note that, because the factory method is called apply, clients can
create queues with an expression such as Queue(1, 2, 3). This
expression expands to Queue.apply(1, 2, 3) since Queue is an object
instead of a function. As a result, Queue looks to clients as if
it was a globally defined factory method. In reality, Scala has no
globally visible methods; every method must be contained in an object
or a class. However, using methods named apply inside global objects,
you can support usage patterns that look like invocations of
global methods.
 */
object FastQueue {
  def apply[T](xs : T*) : FastQueue[T] = new FQueueImpl(xs.toList, Nil)

  private class
  FQueueImpl[T](private val leading : List[T],
                private val trailing : List[T]) extends FastQueue[T] {

    def mirror = if (leading.isEmpty)
                    new FQueueImpl(trailing.reverse, Nil)
                 else
                    this

    def head = mirror.leading.head

    def tail = {
      val q = mirror
      new FQueueImpl(q.leading.tail, q.trailing)
    }

    def enqueue(x : T) = new FQueueImpl(leading, x :: trailing)
  }
}
