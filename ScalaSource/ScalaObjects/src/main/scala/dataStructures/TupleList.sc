def times(chars : List[Char]) : List[(Char, Int)] = {
	def getTimes(chars : List[Char], cmap : List[(Char, Int)]) : List[(Char, Int)] = chars match {
		case List() => cmap
		case y::ys => if (cmap.exists(x => (x._1 == y)))
				getTimes(ys, cmap.map(x => if (x._1 == y) (y, x._2 + 1) else (x._1, x._2)))
			else
				getTimes(ys, (y, 1) :: cmap)
	}
	getTimes(chars, Nil)
}
times(List('a','b','c','c','c','a','e','b','f','e'))


def insertChar(x : (Char,Int), charList : List[(Char,Int)]) : List[(Char,Int)] = charList match {
	case List() => List(x)
	case y :: ys => if (x._2 <= y._2) x :: y :: ys else y :: insertChar(x, ys)
}
def isortFreqTable(freqTable : List[(Char, Int)]) : List[Int] = {
	def sortFreq(freqTable : List[(Char, Int)]) : List[(Char, Int)] = freqTable match {
			case List() => List()
			case x :: xs => insertChar(x, sortFreq(xs))
	}
	sortFreq(freqTable).map(x => x._2)
}
isortFreqTable(times(List('a','b','c','c', 'c', 'a','e','b')))








