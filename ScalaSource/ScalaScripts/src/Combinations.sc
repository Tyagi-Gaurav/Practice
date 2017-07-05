
def permutationsOfEqualLength(x : List[Int]) : Set[List[Int]]= x match {
  case Nil => Set.empty
  case x :: Nil => Set(List(x))
  case x :: xs => permutationsOfEqualLength(xs).flatMap(a => merge(x, a))
}

def permutationsOfAllLengths(x : List[Int]) : Set[List[Int]]= x match {
  case Nil => Set.empty
  case x :: Nil => Set(List(x))
  case x :: xs => {
    val cb = permutationsOfAllLengths(xs)
    cb | cb.flatMap(a => merge(x, a)) | Set(List(x))
  }
}

def permutationsWithFold(x: List[Int]) : Set[List[Int]] = x.fold(Set.empty)((s, item) => {
  val partial = List(item)
  Set(partial) + merge()
}
)

def merge(x : Int, ys : List[Int]) : Set[List[Int]] = {
  (for (
    i <- 0 to ys.length;
    sp = ys.splitAt(i)
  ) yield sp._1 ++ (x :: sp._2)).toSet
}

val testList = List(1, 2, 3)

permutationsOfEqualLength(testList)
permutationsOfAllLengths(testList)

//testList.foldLeft(Set.empty)(cs, item) => )


