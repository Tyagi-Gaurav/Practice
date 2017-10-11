package ikm

/**
  * A New Alphabet has been developed for Internet communications. While the glyphs of the new alphabet
  * don’t necessarily improve communications in any meaningful way, they certainly make us feel cooler.

    You are tasked with creating a translation program to speed up the switch to our more elite New Alphabet
    by automatically translating ASCII plaintext symbols to our new symbol set.
  */
object ProblemB extends App {
  val newAlphabet = Map(
    'a' -> "@", 'b' -> "8", 'c' -> "(", 'd' -> "|)", 'e' -> "3",
    'f' -> "#", 'g' -> "6", 'h' -> "[-]", 'i' -> "|", 'j' -> "_|",
    'k' -> "|<", 'l' -> "1", 'm' -> "[]\\/[]", 'n' -> "[]\\[]", 'o' -> "0",
    'p' -> "|D", 'q' -> "(,)", 'r' -> "|Z", 's' -> "$", 't' -> "']['",
    'u' -> "|_|", 'v' -> "\\/", 'w' -> "\\/\\/", 'x' -> "}{", 'y' -> "`/", 'z' -> "2"
  )

  val line = scala.io.StdIn.readLine()
  val out = line.map(x => newAlphabet.get(x.toLower).getOrElse(x))

  Console.out.println(out.mkString(""))
}
