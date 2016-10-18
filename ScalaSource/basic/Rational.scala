
class Rational(n : Int, d: Int)  {
      require (d != 0)
      override def toString = n +"/" + d
      val numer = n
      val denom = d

      def this(n : Int) = this(n,1)

      def add(that : Rational) : Rational =
      	  new Rational(numer * that.denom + denom * that.numer, denom * that.denom)

      def +(that : Rational) = add(that)

      def *(that : Rational) = new Rational(numer * that.numer, denom * that.denom)

      def +(x : Int) = new Rational(x * denom + numer, denom)
}