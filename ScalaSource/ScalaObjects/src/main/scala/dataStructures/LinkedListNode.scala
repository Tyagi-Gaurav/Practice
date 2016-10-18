package dataStructures

/**
 *
 * @author: tyagig
 */
class LinkedListNode[T](val head: T, val tail : LinkedListTrait[T]) extends LinkedListTrait[T] {
  def isEmpty = false
}
