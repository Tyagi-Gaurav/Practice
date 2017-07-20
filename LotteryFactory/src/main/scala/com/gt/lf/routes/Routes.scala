package com.gt.lf.routes

import java.time.LocalDate

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.StatusCodes._
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.CorsSettings
import com.typesafe.config.Config
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import ch.megard.akka.http.cors.CorsDirectives._
import com.gt.lf.domain.ui.WinnerDecorator._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.gt.lf.repo.WinnerRepo
import spray.json._
import fommil.sjs.FamilyFormats._
import org.joda.time.DateTime

import scala.concurrent.ExecutionContextExecutor

trait Routes {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer
  implicit def config: Config
  implicit val logger: LoggingAdapter

  lazy val winnerRepo = new WinnerRepo(config)

  val settings = CorsSettings.defaultSettings.copy(allowedMethods = Seq(GET, POST, HEAD, OPTIONS, PUT).toList)

  val routes : Route = cors(settings) {
    winnerRoute
  }

  val winnerRoute = path("winner") {
    get {
      complete {
        winnerRepo.getWinnerFor(DateTime.now()).map[ToResponseMarshallable] {
          case Right(winner) => OK -> decorate(winner).toJson
          case Left(ex) => {
            InternalServerError
          }
        }
      }
    }
  }
}
