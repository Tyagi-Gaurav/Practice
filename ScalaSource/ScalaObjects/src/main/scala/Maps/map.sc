
val romanNumerals = Map('I'->1, 'V'->5, 'X'->10)

val capitalOfCountry = Map("US" -> "Washington", "England" -> "London", "India" -> "Delhi")

capitalOfCountry("US")

//Get function on Map
capitalOfCountry get "Andorra"
capitalOfCountry get "US"
def showCapitalFor(country : String) : String = {
	capitalOfCountry.get(country) match {
		case Some(capital) => capital
		case None => "No country found"
	}
}
showCapitalFor("US")
showCapitalFor("USS")

val fruit = List("apple", "pear", "orange", "pineapple")

fruit.sortWith(_.length < _.length)
fruit.sorted

fruit.groupBy(_.head)

val occList = "abcd".toLowerCase().groupBy(x => x).toList
occList.map(x => (x._1, x._2.length)).sorted

fruit.reduceLeft(_+_)

def subset(x : (Char, Int)) : List[List[(Char,Int)]] = x match {
	case (s, 0) => List(List())
	case (s, y) => List((s ,y)) :: subset((s,y-1))
}

def combine(xs : List[(Char,Int)]) : List[List[(Char,Int)]] = xs match {
	case List() => List(Nil)
	case y::Nil => subset(y)
	case y::ys => for(z <- subset(y); r<-combine(ys)) yield z++r
}

combine(List(('k', 2), ('o', 2)))

val lard = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
val r = List(('r', 1))
val lad = List(('a', 1), ('d', 1), ('l', 1))

