package dataStructures

import java.util.NoSuchElementException

/**
 *
 * @author: tyagig
 */
object NilLinkedListNode extends LinkedListTrait[Nothing] {
  def isEmpty = true

  def head : Nothing = throw new NoSuchElementException("Nil:head")
  def tail : Nothing = throw new NoSuchElementException("Nil:tail")
}
