/**
 * Created by gauravt on 28/01/15.
 */

trait Node[T] {
  def getValue : Option[T]

  def add(x: T, f:(T,T) => Int) : Node[T]

  def inOrderTraverse : List[T]

  def search(x :T, f:(T,T) => Int) : Boolean
}

object Node {
  def apply[T](x : T) : Node[T] = new NonEmpty(x, new Empty(), new Empty())

  private class Empty[T] extends Node[T] {
    def add(x : T, f:(T,T) => Int) = new NonEmpty(x, new Empty(), new Empty())

    def getValue = None

    def inOrderTraverse : List[T] = List()

    def search(x :T, f:(T,T) => Int) : Boolean = false
  }

  private class NonEmpty[T](private val x :T,
                            private val left : Node[T],
                            private val right : Node[T]) extends Node[T] {

    def getValue = Some(x)

    def add(v :T, f:(T,T) => Int) = if (f(v,x) >= 0)
                                      new NonEmpty(x, left, right.add(v, f))
                                    else
                                      new NonEmpty(x, left.add(v,f), right)

    def inOrderTraverse : List[T] = left.inOrderTraverse ::: List(x) ::: right.inOrderTraverse

    def search(y :T, f:(T,T) => Int) : Boolean = f(x,y) match {
      case 0 => true
      case 1 => left.search(y, f)
      case -1 => right.search(y, f)
    }
  }
}

