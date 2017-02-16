
object mergeSort extends App {
  def msort(xs : List[Int]) : List[Int] =
    if (xs.length == 0 || xs.length == 1) {
      xs
    } else {
      val (a,b) = xs.splitAt(xs.length/2)
      merge(msort(a), msort(b))
    }

  def merge(alist : List[Int], blist : List[Int]) : List[Int] = alist match {
      case List() => blist
      case x :: xs => blist match {
        case List() => alist
        case y :: ys =>
            if (x < y)
              x :: merge(xs, blist)
            else
              y :: merge(alist, ys)

      }
  }

  println (msort(List(3, 4, 5, 6, 7).reverse))
}

