package com.gt.lf

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.Config

trait AbstractIntegrationSpec {
  val config : Config

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
}
