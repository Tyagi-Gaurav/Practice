
object ReverseListUsingFold extends App {

  def reverseList1(xs : List[Int]) =
    xs.foldLeft(List[Int]())((b : List[Int], y)=> y :: b)

  def reverseList2(xs : List[Int]) =
    xs.foldRight(List[Int]())((y, b : List[Int])=> b :: y)

  println (reverseList1(List(1, 8, 10, 5, 3, 2)))
  println (reverseList2(List(1, 8, 10, 5, 3, 2)))
}
