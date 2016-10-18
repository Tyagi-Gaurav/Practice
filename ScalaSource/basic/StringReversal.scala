

object StringReverser {
       def reverse1(s : String) : String = {
       	   s.reverse
       }

       def reverse2(s : String) : String = {
       	   val builder = new StringBuilder
       	   val len = s.length
	   for (i <- 1 to len) {
	       builder.append(s.charAt(len-i)) 
	   }

	   return builder.toString
       }

       def reverse3(s : String) : String = {
       	   if (s.length == 1) s
	   else
		reverse3(s.substring(1)) + s.charAt(0)
       }

       def main(args:Array[String]) = {
       	   println("Reverse of Hello: " + StringReverser.reverse1("Hello"))
	   println("Reverse of Hello: " + StringReverser.reverse2("Hello"))
	   println("Reverse of Hello: " + StringReverser.reverse3("Hello"))
       }
}