package com.gt.ts

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config : Config) : Timeout = {
    val t = Config.getString("spray.can.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
