import scala.collection.mutable.ArrayBuffer
import scala.util.Random

val random = Random
val b = Array.fill(10) {random.nextInt(10)}

val a = new ArrayBuffer[Int]()
val N = 11
for (i<- 0 to N-1) {
  a+= random.nextInt(N)
}

print (a)

for (i<- 1 until (a.length,2)) {
  val temp = a(i)
  a(i) = a(i-1)
  a(i-1) = temp
}

print (a)


(for {
  i <- a.sliding(2,2)
  j <- i.reverse
} yield j).toArray

