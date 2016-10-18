/**
 * Created by gauravt on 22/01/15.
 */
object ComputeMinMax {
  def min(xs : List[Int]) : Int = {
    xs.foldLeft(Integer.MAX_VALUE)((x,y) => if (x < y) x else y)
  }

  def max(xs : List[Int]) : Int = {
    xs.foldLeft(Integer.MIN_VALUE)((x,y) => if (x > y) x else y)
  }

  def compute[T](xs : List[T], seed : T, f: (T,T) => T) : T = {
    xs.foldLeft(seed)(f(_,_))
  }
}

