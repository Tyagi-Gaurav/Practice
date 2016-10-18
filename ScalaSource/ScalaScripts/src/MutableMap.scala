/**
 *
 * @author: tyagig
 */
import scala.collection.mutable.Map

val treasureMap = Map[Int, String]()

treasureMap += (1 -> "Go to Island.")
treasureMap += (2 -> "Find Big X on Ground")
treasureMap += (3 -> "Dig.")

println(treasureMap(2))

