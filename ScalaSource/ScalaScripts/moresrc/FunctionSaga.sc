val someNumbers = List(-11, -10, -5, 0, 5, 10)
someNumbers.foreach(x => if (x > 0) println(x))

val newNum = someNumbers.filter(x =>x > 0)
val newNum1 = someNumbers.filter(_ > 0)

