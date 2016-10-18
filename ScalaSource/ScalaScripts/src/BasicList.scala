/**
 *
 * @author: tyagig
 */

val oneTwoThree = List("one","two", "three")

val fourFive = List("four","five")

val allLists = oneTwoThree ::: fourFive
println(oneTwoThree)
println(fourFive)
println(allLists)

println("Prepending element: " + 1::allLists)

allLists.foreach(s => println(s))

println("Number of ELements with > 3 letters : " + allLists.count(s => s.length() > 3))
println("List Without First 2 element: " + allLists.drop(2))
println("List Without Last 3 element: " + allLists.dropRight(3))
println("Returns first element of the list: " + allLists.head)
println("Append 'y' to each element: " + allLists.map(s => s + "y"))