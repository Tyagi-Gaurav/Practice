package com.gt.lf

import akka.actor.ActorSystem
import akka.event.NoLogging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import com.gt.lf.domain.ui.{Winner, Winners}
import com.gt.lf.routes.Routes
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{Matchers, WordSpec}
import spray.json._
import fommil.sjs.FamilyFormats._

import scala.concurrent.duration._

class WinnerSpec extends WordSpec with ScalatestRouteTest with Routes with Matchers {
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(10.second)

  override val logger = NoLogging

  override implicit def config: Config = ConfigFactory.load("application_test.conf")

  "GET /winner" should {
    "returns 200 with winner name" in {
      Get("/winner") ~> routes ~> check {
        status shouldBe StatusCodes.OK
        val winners = JsonParser(responseAs[String]).convertTo[Winners]

        winners.winner.size shouldBe 1

        winners.winner(0).id shouldBe "id"
        winners.winner(0).name shouldBe "test"
      }
    }
  }

}
