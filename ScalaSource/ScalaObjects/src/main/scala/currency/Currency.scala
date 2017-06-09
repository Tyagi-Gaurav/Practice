package currency


abstract class CurrencyFactory {
  type Currency <: AbstractCurrency
  def make(amount : Long) : Currency

  abstract class AbstractCurrency {
    val amount : Long
    def designation : String

    override def toString = s"$amount $designation)"

    def + (that : Currency) : Currency = make(this.amount + that.amount)
    def * (x : Double) : Currency = make ((this.amount * x).toLong)
  }
}

object US extends CurrencyFactory {
  abstract class Dollar extends AbstractCurrency {
    override def designation = "USD"
  }
  type Currency = Dollar

  override def make(x: Long): Dollar = new Dollar{val amount = x}
}

