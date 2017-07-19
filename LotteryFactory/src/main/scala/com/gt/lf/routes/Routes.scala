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
import com.gt.lf.domain.ui.WinnerDecorator
import com.gt.lf.repo.WinnerRepo
import spray.json._
import fommil.sjs.FamilyFormats._

import scala.concurrent.ExecutionContextExecutor

trait Routes {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: ActorMaterializer
  implicit def config: Config
  implicit val logger: LoggingAdapter

  val settings = CorsSettings.defaultSettings.copy(allowedMethods = Seq(GET, POST, HEAD, OPTIONS, PUT).toList)

  val routes : Route = cors(settings) {
    winnerRoute
  }

  val winnerRoute = path("winner") {
    get {
      complete {
        WinnerRepo.getWinnerFor(LocalDate.now).map[ToResponseMarshallable] {
          case Right(winner) => OK -> WinnerDecorator.decorate(winner).toJson
          case Left(ex) => {
            InternalServerError
          }
        }
      }
    }
  }
}
