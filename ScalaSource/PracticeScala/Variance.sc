import sun.font.FontRunIterator

/**
 * Created by gauravt on 06/02/15.
 */
//Generic Types are by-default non-variant

//Covariant sub-typing of Queues. i.e. Queue[String] is considered subtype of Queue[AnyRef]
trait Queue_Cov[+T] {

}

//Contravariant sub-typing of Queues. If T is a subtype of S, Queue[S] is a subtype of Queue[T]
trait Queue_Cotv[-T] {

}

abstract class Fruit

class Apple extends Fruit

class Orange extends Fruit

trait Queue[+T] {
  def enqueue[U >: T](x :U) : Queue[U]
}

class AQueue[T] extends Queue[T](
  private val leading : List[T],
  private val trailing : List[T]
) {
  def enqueue[U >: T](x : Fruit) : AQueue[Fruit] =
    new AQueue
}

