package com.gt

import akka.actor.{Actor, Props}

class MyActor extends Actor {
  var log = Logging(context.system, this)

  def receive {
    case "test" => log.info("Received Test")
    case _ => log.info("Received Unknown message")
  }
}

object MyActor extends App {
  val props = Props[MyActor]
}
