package com.gt.lf

import akka.event.NoLogging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.gt.lf.domain.ui.Winner
import com.gt.lf.routes.Routes
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{Matchers, WordSpec}

import spray.json._
import fommil.sjs.FamilyFormats._

class WinnerSpec extends WordSpec with ScalatestRouteTest with Routes with Matchers {

  override val logger = NoLogging

  override implicit def config: Config = ConfigFactory.load("application_test.conf")

  "GET /winner" should {
    "returns 200 with winner name" in {
      Get("/winner") ~> routes ~> check {
        status shouldBe StatusCodes.OK
        val winner = JsonParser(responseAs[String]).convertTo[Winner]

        winner.id shouldBe "id"
        winner.name shouldBe "test"
      }
    }
  }

}
