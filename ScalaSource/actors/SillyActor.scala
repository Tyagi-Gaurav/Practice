
import scala.actors._

object SillyActor extends Actor {
  def act(): Unit = {
    for (i<- 1 to 5) {
      println ("I am acting")
      Thread.sleep(100)
    }
  }
}

object SeriousActor extends Actor {
  def act(): Unit = {
    for (i<- 1 to 5) {
      println ("To be or not to be")
      Thread.sleep(1000)
    }
  }
}

object EchoActor extends Actor {
  def act() = {
    while(true) {
      receive {
        case msg => println ("Received Message " + msg)
      }
    }
  }
}