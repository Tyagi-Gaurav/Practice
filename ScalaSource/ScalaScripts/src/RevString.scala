/**
 *
 * @author: tyagig
 */

def stringReverse(strToReverse : List[Char]) : List[Char] = {
    if (!strToReverse.isEmpty)
      stringReverse(strToReverse.tail) :+ strToReverse.head
    else
        "".toList
}

println(stringReverse("abc".toList).mkString(""))
println(stringReverse("To be or not to be".toList).mkString(""))