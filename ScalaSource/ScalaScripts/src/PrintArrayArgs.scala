/**
 *
 * @author: tyagig
 */

val greetStrings = new Array[String](3)

greetStrings(0) = "Hello"
greetStrings(1) = "World"
greetStrings(2) = "Today !"

for (i <- 0 to 2)
  println(greetStrings(i))
