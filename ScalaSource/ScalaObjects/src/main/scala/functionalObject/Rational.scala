package functionalObject

/**
 *
 * @author: tyagig
 */
class Rational(n: Int, d: Int) extends Ordered[Rational]{
  //Preconditions
  require(d != 0)

  val numer = n
  val denom = d

  //Auxillary Constructor
  def this(n : Int) = this(n, 1)

  def add(that : Rational) : Rational =
    new Rational(numer * that.denom + that.numer * denom,
                 denom * that.denom)

  def + (that : Rational) = add(that)

  def + (that : Int) = add(new Rational(that))

  def - (that : Rational) = add(that * (-1))

  def * (that : Int) : Rational = this * new Rational(that)

  def * (that : Rational) : Rational = new Rational(this.numer * that.numer, this.denom * that.denom)

  def neg : Rational = new Rational(-this.numer, this.denom)

  def lessThan(that : Rational) : Boolean = {
    this.numer * that.denom < that.numer * this.denom
  }

  /*
  It should return an integer that is zero if the objects are the same, negative if receiver is
  less than the argument, and positive if the receiver is greater than the argument.
   */
  def compare(that : Rational) = (this.numer * that.denom) - (this.denom * that.numer)

  //Override toString
  override def toString = n + "/"  + d
}


object RunRational {
  def main(args : Array[String]) = {
    var r1 = new Rational(1,3)
    var r2 = new Rational(2,5)

    println("Adding " + r1 + " to " + r2 + "= " + r1.add(r2))
    println("Adding " + r1 + " to " + r2 + "= " + (r1 + r2))
    println("Multiplying " + r1 + " to " + r2 + "= " + (r1 * r2))
    println("Multiplying " + r1 + " to " + 5 + "= " + (r1 * 5))
    println("Subtracting " + r1 + " from " + r2 + "= " + (r2 - r1))
  }
}