//Find the last element of List
def last(xs : List[Int]) : Int = xs match {
	case Nil => throw new NoSuchElementException
	case y :: Nil => y
	case y::ys => last(ys)
}

last(List(1, 1, 2, 3, 5, 8))

//Find the last but one element of a list.
def penultimate[A](xs : List[A]) : A = xs match {
	case Nil => throw new NoSuchElementException
	case x :: y :: Nil => x
	case y :: ys => penultimate(ys)
}

penultimate(List(1, 1, 2, 3, 5, 8))

//Find the Kth element of a list.
def nth[A](n: Int, xs : List[A]) : A = {
	def findNth(n: Int, ys : List[A]) : A =(n, ys) match {
		case (_, Nil) => throw new NoSuchElementException
		case (0, x::xs) => x
		case (index, a::as) => findNth(index-1, as)
	}

	findNth(n-1, xs)
}

nth(2, List(1, 1, 2, 3, 5, 8))

//Find the number of elements of a list.
def length[A](xs : List[A]) : Int = {
	def findLength(n: Int, xs : List[A]) : Int = xs match {
		case Nil => n
		case x::xs => findLength(n+1, xs)
	}
	
	findLength(0, xs)
}

length(List(1,1,1,1,4,5,6,7))

//Reverse a list.
def reverse[A](xs :List[A]) : List[A] = xs match {
	case Nil => Nil
	case x::xs => reverse(xs) ::: List(x)
}

reverse(List(1, 1, 2, 3, 5, 8))

//Find out whether a list is a palindrome.
def isPalindrome1(xs : List[Int]) : Boolean = {
	def checkPalindrome(start : Int, xs : List[Int], end : Int) : Boolean = (start, xs, end) match {
		case (s, Nil, e) => false
		case (s, ys, e) if (s > e) => ys(s) == ys(e)
		case (s, ys, e) if (ys(s) == ys(e)) => checkPalindrome(s+1, ys, e-1)
		case default => false
	}

	checkPalindrome(0, xs, length(xs)-1)
}

isPalindrome1(List(8,7, 1, 2, 3, 3, 2, 1, 7,8)) //True
isPalindrome1(List(8,7, 1, 2, 3, 4, 2, 1, 7,8)) //False

def isPalindrome2(xs : List[Int]) : Boolean = {
	xs == reverse(xs)
}

isPalindrome2(List(8,7, 1, 2, 3, 3, 2, 1, 7,8)) //True
isPalindrome2(List(8,7, 1, 2, 3, 4, 2, 1, 7,8)) //False

//Flatten a nested list structure.
def flatten(xs : List[Any]) : List[Any] = xs match {
	case (y : List[Any])::ys => List() ::: flatten(y) ::: flatten(ys)
	case (y : Any) :: ys => y :: flatten(ys)
	case Nil => List()
}

flatten(List(List(1, 1), 2, List(3, List(5, 8))))

// Pack consecutive duplicates of list elements into sublists.
def compress(xs : List[Symbol]) : List[Symbol] = xs match {
	case Nil => Nil
	case x::Nil => x::List()
	case x::y::ys if (x == y) => compress(x::ys)
	case x::y::ys if (x != y) => x::compress(y::ys)
}
compress(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))

//Run-length encoding of a list.
def encode(xs : List[Symbol]) : List[(Int, Symbol)] = {
	def createEncoding(ys : List[(Int, Symbol)]) : List[(Int, Symbol)] = ys match {
		case Nil => Nil
		case (num,x)::Nil => (num,x)::List()
		case (n1,x)::(n2,y)::ys if (x == y) => createEncoding((n1+n2,x)::ys)
		case (n1,x)::(n2,y)::ys if (x != y) => (n1,x)::createEncoding((n2,y)::ys)
	}

	val intList = List.fill(xs.length)(1)
	createEncoding(intList.zip(xs))
}

encode(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))

//Modified run-length encoding.
def encode2(xs : List[Symbol]) : List[Any] = {
	def createEncoding(ys : List[(Int, Symbol)]) : List[Any] = ys match {
		case Nil => Nil
		case (num,x)::Nil => 
				if (num ==1) x::List() else (num,x)::List()
		case (n1,x)::(n2,y)::ys if (x == y) => createEncoding((n1+n2,x)::ys)
		case (n1,x)::(n2,y)::ys if (x != y) => 
				if (n1 == 1)
					x::createEncoding((n2,y)::ys)
				else
					(n1,x)::createEncoding((n2,y)::ys)
	}

	val intList = List.fill(xs.length)(1)
	createEncoding(intList.zip(xs))
}

encode2(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e))

//Decode a run length encoding
def decode(xs : List[(Int, Symbol)]) : List[Symbol] = xs match {
	case Nil => Nil
	case (1,x) :: Nil => x :: Nil
	case (p,x) :: Nil => x :: decode(List((p-1,x)))
	case (1,x) :: xs => x :: decode(xs)
	case (p,x) :: xs => x :: decode((p-1,x) :: xs)
}

decode(List((4, 'a), (1, 'b), (2, 'c), (2, 'a), (1, 'd), (4, 'e)))

//Duplicate elements of a list
def duplicate[A](xs : List[A]) : List[A] = xs match {
	case Nil => Nil
	case x::xs => x::x::duplicate(xs)
}

//duplicateN
def duplicateN[A](n : Int, xs : List[A]) : List[A] = xs match {
	case Nil => Nil
	case x::xs => List.fill(n)(x) ::: duplicateN(n, xs)
}

//Drop every Nth element from a list
def drop(n : Int, xs : List[Symbol]) : List[Symbol]= {
	if (n < xs.length)
		xs.take(n-1) ::: drop(n, xs.drop(n))
	else
		xs
}
drop(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//Split a list into two parts
def split[A](n : Int, xs : List[A]) : (List[A], List[A]) = {
	(xs.take(n), xs.drop(n))
}
split(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//Rotate a list N places to the left.
def rotate[A](n : Int, xs : List[A]) : List[A] = {
	def rotateOnce(xs:List[A]) : List[A] = xs.drop(1) ::: List(xs.head)
	
	if (n <= 0) xs
	else rotate(n-1, rotateOnce(xs))
}
rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))

//Remove the Kth element from a list.
def removeKth[A](k : Int, xs : List[A]) : (A, List[A]) = (k, xs) match {
	case (k, xs) if (k <=0) => throw new NoSuchElementException
	case (k, Nil) => throw new NoSuchElementException
	case (1, x::xs) => (x, xs)
	case (k, x::xs) => val (a,b) = removeKth(k-1, xs)
					(a, x::b)	
}

removeKth(1, List('a, 'b, 'c, 'd))

//Insert an element at a given position into a list
def insertAt[A](y : A, k : Int, xs : List[A]) : List[A] = (k,xs) match {
	case (k, xs) if (k <=0) => xs
	case (k, Nil) => Nil
	case (1, x::xs) => y::xs
	case (k, x::xs) => x::insertAt(y, k-1, xs)
}

insertAt('new, 1, List('a, 'b, 'c, 'd))

//Extract a given number of randomly selected elements from a list
def randomSelect[A](n : Int, xs : List[A]) : List[A] = {
	if (xs.length == 0) throw new NoSuchElementException
	
	def generateRandomList[A](n : Int, r : scala.util.Random, xs:List[A]) : List[A] = {
		if (n <= 0) 
			Nil
		else {
			val (a,b) = removeKth(r.nextInt(xs.length)+1, xs)
			a::generateRandomList(n - 1, r, b)
		}
	}
	
	generateRandomList(n, new scala.util.Random(), xs)
}

randomSelect(3, List('a, 'b, 'c, 'd, 'f, 'g, 'h))

//Generate a random permutation of the elements of a list
def randomPermute[A](xs : List[A]) : List[A] = {
	randomSelect(xs.length, xs)
}

randomPermute(List('a, 'b, 'c, 'd, 'e, 'f))

//Generate the combinations of K distinct objects chosen from the N elements of a list.
def gen[A](n : Int, xs : List[A]) : List[List[A]] = {
	if (n > 1) {
		combinations(n, xs)
	} else {
	    xs.map(x => List(x))
	}
}

def combinations[A](n : Int, ys : List[A]) : List[List[A]] = {
	ys match {
		case Nil => Nil
		case x::xs if (n > (x::xs).length) => Nil
		case x::xs if (n <= (x::xs).length) => 
			(gen(n-1, xs)).map(y => x::y) ::: combinations(n, xs)
	}
}

println (combinations(3, List('a, 'b, 'c, 'd, 'e, 'f)))

//Sorting a list of lists according to length of sublists.
def sortListByLength[A](xs : List[List[A]]) : List[List[A]] = {
	def sortList(xs : List[List[A]], sortedxs : List[List[A]]) : List[List[A]] = xs match {
		case Nil => sortedxs
		case x::xs => val newSorted = putInSortedArray(x, sortedxs);
					  sortList(xs, newSorted)
	}
	
	def putInSortedArray(x : List[A], sortedxs : List[List[A]]) : List[List[A]] = {
		if (sortedxs.length > 0) {
			val (front, end) = sortedxs.span(y => y.length < x.length)
			front ::: (x:: end)
		} else {
			List(x)
		}
	}
	
	sortList(xs, List())
}
 
println (sortListByLength(List(List('a, 'b, 'c), List('d, 'e), List('f, 'g, 'h), List('d, 'e), List('i, 'j, 'k, 'l), List('m, 'n), List('o)))) 

//Determine whether a given integer number is prime.
def isPrime(x : Int) : Boolean = {
	!((2 to x/2).toStream.takeWhile(_ < x).exists(x%_ == 0))
}

println ("is 7 Prime: " + isPrime(7))
println ("is 2 Prime: " + isPrime(2))
println ("is 3 Prime: " + isPrime(2))
println ("is 10 Prime: " + isPrime(10))
println ("is 100 Prime: " + isPrime(100))
println ("is 729 Prime: " + isPrime(729))
println ("is 229 Prime: " + isPrime(229))
println ("is 243 Prime: " + isPrime(243))
println ("is 241 Prime: " + isPrime(241))

//Determine the prime factors of a given positive integer
def primeFactors(x : Int) : List[Int] = {
	val result = for (i <- 2 to x/2;
		 if x%i == 0 && isPrime(i)) yield {i}
	result.toList
}

println (primeFactors(42))
println (primeFactors(10))
println (primeFactors(315))

//Goldbach's Conjecture
def goldbach(x : Int) : List[(Int, Int)] = {
	val primeList = (2 to x-1).toStream.takeWhile(_ < x).filter(isPrime(_)).toList
	for (y <- primeList;
		 j <- primeList.filter(z => x - y == z))
				 yield {(y,j)}
}

println (goldbach(28))

//Find Frequency of each letter in a word.