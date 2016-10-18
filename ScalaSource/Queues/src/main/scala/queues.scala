
class Queue[T] private ( 
  /* 
   The private modifier here between class & parameters means that it 
   can only be accessed from within the class or companion object.
   */

  private val leading  : List[T],
  private val trailing : List[T]) {

  def mirror = if (leading.isEmpty) new Queue(trailing.reverse, Nil) else this

  def head = mirror.leading.head

  def tail = {
    val q = mirror
    new Queue(q.leading.tail, q.trailing)
  }

  def enqueue(x : T) = new Queue(leading, x::trailing)
}

object Queue {
  /*
   Creating an apply method which acts as a factory method for
   creating Queue Objects.
   */
  def apply[T](xs : T*) = new Queue(xs.toList, Nil)
}
`
