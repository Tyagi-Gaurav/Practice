package dataStructures

/**
 *
 * @author: tyagig
 */
object LinkedListObject {
    def singleton[T](elem : T) = new LinkedListNode[T](elem , NilLinkedListNode)

    def main(args : Array[String]) {
      val list = new LinkedListNode(1, new LinkedListNode(2, new LinkedListNode(3, NilLinkedListNode)))
      println(nth(1, list))
			println("**** Following error is Expected *****")
			println(nth(4, list))
    }

    def nth[T](n : Int, xs: LinkedListTrait[T]) : T = {
      if (xs.isEmpty) throw new IndexOutOfBoundsException()
      else if (n == 0) xs.head
      else nth(n-1, xs.tail)
    }
}
