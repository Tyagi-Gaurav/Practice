
object Conversions extends App {
  def inchesToCentimeters(x : Double) = x * 2.54

  print(inchesToCentimeters(2))
 }

abstract class UnitConversion {
  def convert(x : Double) : Double
}

class InchesToCentimeters extends UnitConversion {
  override def convert(x: Double): Double = x * 2.54
}

class MilesToKilometers extends UnitConversion {
  override def convert(x: Double): Double = x * 1.6
}
