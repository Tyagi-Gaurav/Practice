class Counter {
  private var value = 0

  def increment(): Unit = {
    value += 1
  }

  def isLess(other : Counter) = value < other.value // We can still access other objects private field ??
}

class Counter2 {
  private[this] var value = 0 //Other objects cannot access this now. Object Private

  def increment(): Unit = {
    value += 1
  }
}


class Person {
  private var name = ""
  private var age = 0

  def this(name: String) {  // An auxiliary constructor
    this()
    this.name = name
  }
}

val p1 = new Person()
val p2 = new Person("G")

class Person2(val name : String, val age: Int) { // Primary Constructor

}

class Person3(name: String, age: Int) { // declares and initializes immutable fields name and age that are object-private.
  def description = name + " is " + age + " years old"
}

