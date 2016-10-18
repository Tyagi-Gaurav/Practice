package models

import com.mongodb.casbah.Imports._
import collection.immutable.StringOps

/**
 * Created with IntelliJ IDEA.
 * User: tyagig
 * Date: 24/09/14
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
object DatabaseClient {
  val port = play.Play.application.configuration.getString("mongo.port")
  val mongoClient =
    MongoClient(play.Play.application.configuration.getString("mongo.host"),
      (new StringOps(port).toInt))

  val mongoDBInstance = mongoClient.getDB(play.Play.application.configuration.getString("mongo.database"))
}
