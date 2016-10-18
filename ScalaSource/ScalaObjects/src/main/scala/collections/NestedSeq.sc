object pairs {
	val n = 7
	def isPrime(x : Int) : Boolean = (2 until x).forall(d => x%d != 0)

	(1 until n).flatMap (i=>
		(1 until i).map(j => (i,j))) filter(z => isPrime(z._1 + z._2))

	for {i <- 1 until n
			 j <- 1 until i
			 if isPrime(i + j)
	} yield (i,j)

	def scalarProduct(xs : List[Double], ys : List[Double]) : Double =
		(for {(x,y) <- xs zip ys
		} yield (x*y)).sum
}
