package treestruct

/**
 *
 * @author: tyagig
 */
trait Tree[T<:Int] {
  def contains(x : T) : Boolean
  def add(x: T) : Tree[T]
  def join(that : Tree[T]) : Tree[T]
  def filter(f : T => Boolean) : Tree[T]
}

class EmptyTree[T<:Int] extends Tree[T] {
  def contains(x : T) = false

  def add(x : T) = new NonEmptyTree(x , new EmptyTree, new EmptyTree)

  def filter(f:T=>Boolean) = new EmptyTree

  def join(that : Tree[T]) =  that

  override def toString : String = ""
}

class NonEmptyTree[T<:Int](val elem : T, val left : Tree[T], val right: Tree[T]) extends Tree[T]  {
  def contains(node : T) = if (node < elem) left.contains(node)
                        else if (node > elem) right.contains(node)
                        else true

  def add(node : T)  = if (node < elem) new NonEmptyTree(elem, left.add(node), right)
                          else if (node > elem) new NonEmptyTree(elem, left, right.add(node))
                          else this

  def join(that : Tree[T]) =  {
      var newTree = that.add(this.elem)
      var joinedLeftTree = this.left.join(newTree)
      this.right.join(joinedLeftTree)
    }

  def filter(f:T=>Boolean) = if (f(elem)) new NonEmptyTree(elem, left.filter(f), right.filter(f))
                              else (left.filter(f)).join(right.filter(f))

  override def toString : String = "[" + left + "]," + elem + ",["+ right +"]"

}