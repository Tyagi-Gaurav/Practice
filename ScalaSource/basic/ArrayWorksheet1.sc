import scala.util.Random

val N = 10
val random = Random

val a = Array(-1,2,-3, 0, 4,-5,6,-7,8,-9)

val positiveindexes = for(i<-0 until a.length if a(i) > 0) yield i

val negindexes = for(i<-0 until a.length if a(i) < 0) yield i

val d = Array(1.0, 2.0, 3.0, 4.0, 5.10, 6.0, 7.0, 8.0)

val sum = d.foldLeft(0.0)((x, y) => x + y)
val avg = sum/d.length

val b = Array(1,1,3,4,5,2,6,9,6,7,7,2,2,2,9)

b.
