package com.gt.lf

import akka.actor.ActorSystem
import akka.event.NoLogging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import com.github.simplyscala.{MongoEmbedDatabase, MongodProps}
import com.gt.lf.domain.ui.{Winner, Winners}
import com.gt.lf.routes.Routes
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfter, Matchers, WordSpec}
import spray.json._
import fommil.sjs.FamilyFormats._
import org.joda.time.DateTime
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future
import scala.concurrent.duration._

class WinnerSpec extends WordSpec
  with ScalatestRouteTest
  with Routes
  with Matchers
  with BeforeAndAfter
  with MongoEmbedDatabase {
  implicit def default(implicit system: ActorSystem) = RouteTestTimeout(10.second)

  override val logger = NoLogging
  var mongoInstance : MongodProps = null

  override implicit def config: Config = ConfigFactory.load("application_test.conf")

  before {
    try {
      mongoInstance = mongoStart(27017)
      val dbUri = config.getString("dbUri")
      val driver = MongoDriver(config)
      val parsedURI = MongoConnection.parseURI(dbUri)
      val connection = parsedURI.map(driver.connection(_))

      val futureConnection = Future.fromTry(connection)
      def lotteryDb : Future[DefaultDB] = futureConnection.flatMap(_.database(config.getString("dbName")))
      def winnerCollection : Future[BSONCollection] = lotteryDb.map(_.collection("winner"))

      val document1 = BSONDocument(
        "id" -> "id",
        "date" -> "2017-01-01",
        "value" -> "test"
      )

      winnerCollection.map(_.insert(document1))
    }
    catch { case ex:Exception =>
        ex.printStackTrace()
    }

  }

  after {
    mongoStop(mongoInstance)
  }

  "GET /winner" should {
    "returns 200 with winner name" in {
      Get("/winner?date=2017-01-01") ~> routes ~> check {
        status shouldBe StatusCodes.OK
        val winners = JsonParser(responseAs[String]).convertTo[Winners]

        winners.winner.size shouldBe 1

        winners.winner(0).id shouldBe "id"
        winners.winner(0).name shouldBe "test"
      }
    }
  }

}
