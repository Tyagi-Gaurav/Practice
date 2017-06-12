
def combinationsOfEqualLength(x : List[Int]) : Set[List[Int]]= x match {
  case Nil => Set.empty
  case x :: Nil => Set(List(x))
  case x :: xs => combinationsOfEqualLength(xs).flatMap(a => merge(x, a))
}

def combinationsOfAllLengths(x : List[Int]) : Set[List[Int]]= x match {
  case Nil => Set.empty
  case x :: Nil => Set(List(x))
  case x :: xs => {
    val cb = combinationsOfAllLengths(xs)
    cb | cb.flatMap(a => merge(x, a)) | Set(List(x))
  }
}

def merge(x : Int, ys : List[Int]) : Set[List[Int]] = {
  (for (
    i <- 0 to ys.length;
    sp = ys.splitAt(i)
  ) yield sp._1 ++ (x :: sp._2)).toSet
}

combinationsOfEqualLength(List(1, 2, 3))
combinationsOfAllLengths(List(1, 2, 3))


