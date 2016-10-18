import java.util.Date

/**
 * Created by gauravt on 20/01/15.
 */
case class Person(weight : Double, weightEnterDt : Date)

def calculateAvgWeight(begin : Date, end : Date, plist : List[Person]) : Option[Double] = {
  def calculate(sum : Double, count : Double) : Option[Double] = plist match {
    case List() => if (count == 0.0) None else Option(sum/count)
    case x :: plist =>
      if (x.weightEnterDt.after(begin) && x.weightEnterDt.before(end))
        calculate(count+1,sum+x.weight)
      else
        calculate(count,sum)
  }

  return calculate(0.0, 0.0)
}

val dt1 = new java.util.Date()
val dt2 = new java.util.Date(2015, 1, 10)
val dt3 = new java.util.Date(2014, 12, 10)
val dt4 = new java.util.Date(2014, 11, 10)

val plist = List(
        Person(10.0, dt1),
        Person(20.0, dt2),
        Person(30.0, dt3),
        Person(40.0, dt4)
)

val beginDt = new Date(2014, 1, 19)
val endDt = new Date(2014, 12, 19)

calculateAvgWeight(beginDt, endDt, plist)