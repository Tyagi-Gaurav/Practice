
import akka.actor.ActorSystem

object WebServer {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("my-system")
    implicit val material = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`))
        }
      }
  }
}
