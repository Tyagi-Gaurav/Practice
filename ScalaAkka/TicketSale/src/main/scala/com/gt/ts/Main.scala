package com.gt.ts

import scala.concurrent.Future
import akka.actor.{ActorSystem, Actor, Props}
import akka.event.Logging
import akka.util.Timeout

import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import com.typesafe.config.{Config, ConfigFactory}

object Main extends App with RequestTimeout {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getString("http.port")

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher

  val api = new RestApi(system, requestTimeout(config)).routes

  implicit val materializer = ActorMaterializer()
  val bindingFuture: Future[ServerBinding] = Http().bindAndHandle(api, host, port)
}
