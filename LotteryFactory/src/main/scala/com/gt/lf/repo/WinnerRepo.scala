package com.gt.lf.repo

import com.typesafe.config.Config
import org.joda.time.DateTime
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}
import reactivemongo.bson.{BSONDocumentReader, Macros}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import reactivemongo.bson.document

sealed trait appDomain;

case class Winner(id : String, value : String, date: String) extends appDomain;

class WinnerRepo(config: Config) {
  val dbUri = config.getString("dbUri")

  val driver = MongoDriver(config)
  val parsedURI = MongoConnection.parseURI(dbUri)
  val connection = parsedURI.map(driver.connection(_))

  val futureConnection = Future.fromTry(connection)
  def lotteryDb : Future[DefaultDB] = futureConnection.flatMap(_.database(config.getString("dbName")))
  def winnerCollection : Future[BSONCollection] = lotteryDb.map(_.collection("winner"))

  implicit def winnerReader: BSONDocumentReader[Winner] = Macros.reader[Winner]

  def getWinnerFor(d : DateTime) : Future[Either[Throwable, List[Winner]]] = {
    winnerCollection.flatMap(
      _.find(document("date" -> d.toLocalDate.toString)).cursor[Winner]().collect[List](1))
      .map(Right(_))
  }
}

