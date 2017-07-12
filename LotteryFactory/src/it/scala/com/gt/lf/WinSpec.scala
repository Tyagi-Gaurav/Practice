package com.gt.lf

import akka.http.scaladsl.unmarshalling.Unmarshal
import com.gt.lf.domain.ui.Winner
import com.typesafe.config.ConfigFactory
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import spray.json.JsonParser
import scala.concurrent.ExecutionContext.Implicits.global

import spray.json.JsonParser
import fommil.sjs.FamilyFormats._

import scala.concurrent.Await
import scala.concurrent.duration._

class WinSpec extends FeatureSpec with AbstractIntegrationSpec with GivenWhenThen with Matchers {
  implicit override val config = ConfigFactory.load("integration_test.conf")

  feature("System should return the current winner") {
    scenario("should retrieve winner for today") {
      When("user makes request to retrieve current winner")
      val endpoint = config.getString("service.win_endpoint")
      val responseFuture = RestClient(endpoint).makeRequest

      Then("Current winner details should be returned")
      val response = Await.result(responseFuture, 5 seconds)
      val winner = Unmarshal(response.entity)
          .to[String].map(JsonParser(_).convertTo[Winner])

      val dwinner = Await.result(winner, 2 seconds)
      dwinner.id shouldBe 1
    }
  }
}
