def reverse3(s : String) : String = {
    if (s.length == 1) s
  else
    reverse3(s.drop(1)) + s.take(1)
}

val s = "gfg"
s.take(1)

reverse3("abc")

for (counter <- 1 to 10)
  println(counter)

for (i<- 1 to 10) yield i % 3

def fact(x :Int) : Int = if (x <= 0) 1 else (x * fact(x - 1))

fact(5)
fact(7)

def recursiveSum(args: Int*) : Int =
  if (args.length == 0) 0
  else
    args.head + recursiveSum(args.tail:_*)

recursiveSum(1 to 10:_*)

for (i <- 0 to 10) println (10 - i)
for (i <- 10 to 0 by -1) println (i)

def calc(s : String) : Long =
  if (s.length == 0) 1
  else
  s.charAt(0) * calc(s.drop(1))

calc("Hello")

def calc_power(x :Int, n : Int) : Double =
  if (n == 0) 1
  else if (n < 0)
    1/ calc_power(x, -1 * n)
  else if (n%2 == 0)
    calc_power(x, n/2) * calc_power(x, n/2)
  else if (n%2 == 1)
    x  * calc_power(x, n-1)
  else -1

calc_power(10, 1)
calc_power(10, 2)
calc_power(10, 4)
calc_power(10, -1)
calc_power(45, -1)

val b = Array(1,2,3,1,2,3,3,2,4)
b.distinct

var distinct_set = Set[Int]()

for(i<-0 until(b.length) if (!distinct_set.contains(b(i)))) yield {
  distinct_set += b(i)
  b(i)
}












