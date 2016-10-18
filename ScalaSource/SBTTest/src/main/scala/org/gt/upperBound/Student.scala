package org.gt.upperBound

/**
 * Created by gauravt on 17/03/15.
 */
class Student(val firstName : String, val lastName : String, val marks : Int) extends Ordered[Student]{
  override def compare(that: Student): Int = this.marks.compareTo(that.marks)

  override def toString() = firstName + " " + lastName + "-" + marks
}

object Student {
  def orderedMergeSort[T <: Ordered[T]](xs : List[T]) : List[T] = {

    def merge(ys : List[T], zs : List[T]) : List[T] = (ys, zs) match {
      case (Nil, _) => zs
      case (_, Nil) => ys
    }

    val n = xs.length / 2
    if (n == 0) xs
    else {
      val (f,l) = xs splitAt n
      merge(orderedMergeSort(f), orderedMergeSort(l))
    }
  }
}