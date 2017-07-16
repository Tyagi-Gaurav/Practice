
class Rational(n : Int, d : Int) {
  require (d != 0)
  val numer = n
  val denom = d
  override def toString = n + "/" + d

  def this(n : Int) = this (n, 1)

  def add (that : Rational) : Rational =
    new Rational(numer * that.denom +
                 that.numer * denom,
          denom * that.denom)

  def lessThan (that : Rational) : Boolean =
    numer * that.denom < denom * that.numer

  def max (that : Rational) : Rational =
    if (this.lessThan(that))
      that
    else
      this
}

val r1 = new Rational(1, 2)
val r2 = new Rational(2, 3)

val r3 = r1 add r2

val r4 = r1 max r2

val r5 = new Rational(5)

//new Rational(5, 0) -- Requirement would fail
