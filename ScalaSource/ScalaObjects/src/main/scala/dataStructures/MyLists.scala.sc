val fruit1 = List("apples", "oranges", "pairs")
val empty1 = List()

val diag3 = List(List(1,0,0), List(0,1,0), List(0,0,1))

//:: is associated to the right
// Nil.::(4).::(3).::(2).::(1)
val nums = 1::2::3::4::Nil
val fruit2 = "apples" :: "oranges" :: "pears" :: Nil
val empty2 = Nil

fruit1.head
fruit1.tail

//empty1.head //Throws Exception
//Pattern Matching for Lists
def listPatternMatch[T](myList : List[T]) = myList match {
	case Nil => println("Nil") // Lists of Length 0
	case x :: Nil => x //Lists of Length 1
	case p :: ps => p //Lists of Length > 1
}

listPatternMatch(fruit1)
listPatternMatch(empty1)

listPatternMatch(nums)

def insert(x : Int, xs: List[Int]) : List[Int]= xs match {
	case List() => List(x)
	case y :: ys => if (x <= y) x :: xs else y :: insert(x,ys)
}

def isort(ls : List[Int]) : List[Int] = ls match {
	case List() => List()
	case y::ys => insert(y, isort(ys))
}

isort(List(7,9,173,1))

