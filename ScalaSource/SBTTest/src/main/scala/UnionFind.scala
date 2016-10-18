/**
 * Created by gauravt on 31/01/15.
 */
trait UnionFind[T] {
  def union(x : T, y :T) : UnionFind[T]

  def contains(x :T) : Boolean

  def connected(x :T, y :T) : Boolean
}

private case class UF[T](xs : Set[T]) {
  def contains(x :T) = xs.contains(x)

  def +(x :T) = UF(xs + x)

  def +(ys : Set[T]) = UF(xs.union(ys))

  def +(zs : UF[T]) : UF[T] = UF(this.xs.union(zs.xs))
}

object UnionFind {
  def apply[T](xs : T*) : UnionFind[T] = UnionFindImpl(xs.toList.map(x => UF(Set() + x)))

  private case class UnionFindImpl[T](private val xs : List[UF[T]]) extends UnionFind[T] {

    def find(a :T, b :T, ys : List[UF[T]]) : (List[UF[T]], List[UF[T]]) = ys match {
      case List() => (List(), List())
      case z :: zs => val r = find(a, b, zs)
                      if (z.contains(a) || z.contains(b))
                          (r._1 ::: List(z), r._2)
                      else
                        (r._1, List(z) ::: r._2)

    }

    def union(a :T, b:T) : UnionFind[T] = {
      val (first,last) = find(a, b, xs)
      UnionFindImpl(List(first(0)+first(1)) ::: last)
    }

    def contains(x: T) : Boolean = {
      xs.exists(y => y.contains(x))
    }

    def connected(a :T, b: T) : Boolean = {
       def connected_int(a :T, b: T, zs : List[UF[T]]) : Boolean = zs match {
         case List() => false
         case y :: ys => if (y.contains(a) && y.contains(b)) true
         else connected_int(a, b, ys)
       }

      connected_int(a, b, xs)
    }
  }
}