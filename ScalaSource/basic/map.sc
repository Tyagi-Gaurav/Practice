
val a = Map("phone"->7, "laptop"->10)

val in = new java.util.Scanner(new java.io.File("/Users/gtyagi/personalWorkspace/Practice/ScalaSource/basic/cite_mini.txt"))
var m = Map[String, String]()
while (in.hasNext()) {
  val b = in.next().split(",")
  m += (b(0) -> b(1))
}

for ((k,v) <- m)
  println (k + ":" + v)




