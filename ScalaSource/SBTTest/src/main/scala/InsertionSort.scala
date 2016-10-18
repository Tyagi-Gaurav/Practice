/**
 * Created by gauravt on 26/01/15.
 */
object InsertionSort {
  def sort[T](xs : List[T], f:(T,T) => Boolean) : List[T] = xs match {
    case List() => List()
    case x :: xs => insert(x, sort(xs, f), f)
  }

  def insert[T](x : T, xs : List[T], f: (T,T) => Boolean) : List[T] = xs match {
    case List() => List (x)
    case y :: ys if f(x,y) => x :: y :: ys
    case y :: ys if (!f(x,y)) => y :: insert(x, ys, f)
  }
}
