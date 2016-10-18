val xs = Stream.cons(1, Stream.cons(2, Stream.empty))

val nxs = (1 to 1000).toStream

//(lo until hi).toStream
def streamRange(lo : Int, hi: Int) : Stream[Int] = {
	print(lo + " ")
	if (lo >= hi) Stream.empty
	else Stream.cons(lo, streamRange(lo + 1, hi))
}

def listRange(lo : Int, hi: Int) : List[Int] =
	if (lo >= hi) Nil
	else lo::listRange(lo, hi)
val sr = streamRange(1, 1000)
sr.head
sr.tail
//val hashStream = 1#::2#::3

streamRange(1, 10).take(3).toList

lazy val x = {print("x"); 1}

def from (n : Int) : Stream[Int] = n #:: from(n+1)

val nats = from(0)
val m4s = nats map(_ * 4)

(m4s take 10).toList

def sieve(s : Stream[Int]) : Stream[Int] =
	s.head #:: sieve(s.tail filter (_ % s.head != 0))

val primes = sieve(from(2))

primes.take(100).toList
