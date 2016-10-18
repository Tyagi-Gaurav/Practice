
import math.Ordering
object OrderingMergeSort {
	def msort[T](xs : List[T])(implicit ord:Ordering[T]) : List[T] = {
		val n = xs.length / 2
		if (n ==0) xs
		else {
			def merge(xs : List[T], ys: List[T]) : List[T] = {
				(xs,ys) match {
					case (Nil, ys) => ys
					case (xs, Nil) => xs
					case (x::xs1, y::ys1)=>
						if (ord.lt(x,y)) x::merge(xs1,ys) else y::merge(xs, ys1)
				}
			}

			val (fst, snd) = xs.splitAt(n)
			merge(msort(fst),msort(snd))
		}
	}
	val nums = List(2,-4,5,7,1,9,16,3)
	msort(nums)

	val fruits = List("apples", "bananas", "pineapple", "orange")
	msort(fruits)
}