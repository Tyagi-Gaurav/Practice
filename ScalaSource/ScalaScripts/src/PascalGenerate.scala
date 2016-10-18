/**
 *
 * @author: tyagig
 */

var row1 = List(1)
var lastRow = 1 :: row1

row1.foreach(s => println(s))
var i=0
while (i < 20) {
  lastRow.foreach(s => print(s + " "))
  println
  lastRow = row1 ::: lastRow.sliding(2).map{case Seq(x, y) => x+y }.toList ::: row1
  i = i +1
}


