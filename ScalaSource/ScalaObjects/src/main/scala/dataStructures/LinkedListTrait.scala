package dataStructures

/**
 *
 * @author: tyagig
 */
trait LinkedListTrait[+T] {
  def isEmpty : Boolean
  def head : T
  def tail : LinkedListTrait[T]
	def prepend[U >: T](elem : U) : LinkedListTrait[U] = new LinkedListNode(elem, this)
}
