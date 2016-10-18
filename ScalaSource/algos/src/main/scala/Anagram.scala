
case class Anagram(word : String) {
	def verify(tw : String) : Boolean = {
		val freq = calculateFrequency(word.toList)
		if (tw.length == 0) false
		isAnagram(tw.toList, freq)
	}
	
	def isAnagram(tw : List[Char], freq : List[(Char, Int)]) : Boolean = {
		tw match {
			case List() => true
			case x::xs => if (freq.exists(y => y._1 == x && y._2 > 0))
							isAnagram(xs, freq.map(y => if (y._1 == x) (x, y._2 - 1) else (y._1, y._2)))
						  else
							false
		}
	}
	
	def calculateFrequency(char : List[Char]) : List[(Char,Int)] = {
		def getTimes(char : List[Char], tmap : List[(Char, Int)]) : List[(Char, Int)] = {
			char match {
				case List() => tmap
				case y::ys => if (tmap.exists(x => x._1 == y))
								getTimes(ys, tmap.map(x => if (x._1 == y) (y, x._2 + 1) else (x._1, x._2)))
							  else
								getTimes(ys, (y, 1) :: tmap)
			}
		}
		
		getTimes(char, Nil)
	}	
}
