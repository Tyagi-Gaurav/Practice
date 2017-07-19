package com.gt.lf.repo

import java.time.LocalDate

import com.gt.lf.domain.app.Winner
import com.typesafe.config.Config
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.ExecutionContext.Implicits.global
import spray.json._
import fommil.sjs.FamilyFormats._

import scala.concurrent.Future

class WinnerRepo(config: Config) {
  val dbUri = config.getString("dbUri")

  val driver = MongoDriver(config)
  val parsedURI = MongoConnection.parseURI(dbUri)
  val connection = parsedURI.map(driver.connection(_))

  val futureConnection = Future.fromTry(connection)
  def lotteryDb : Future[DefaultDB] = futureConnection.flatMap(_.database("lottery"))
  def winnerCollection = lotteryDb.map(_.collection("winner"))
}

object WinnerRepo {
  def getWinnerFor(d : LocalDate) : Future[Either[Throwable, Winner]] =
    Future.successful(Right(Winner("id", "test")))
}