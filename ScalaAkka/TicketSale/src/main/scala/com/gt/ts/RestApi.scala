package com.gt.ts

import akka.actor.ActorRef
import akka.util.Timeout

trait BoxOfficeApi {
  import BoxOffice._

  def createBoxOffice() : ActorRef

  implicit def executionContext : ExecutionContext
  implicit def timeout : Timeout

  lazy val boxoffice = createBoxOffice()

  def createEvent(event : String, nrOfTickets: Int) =
    boxoffice.ask(CreateEvent(event, nrOfTickets))
              .mapTo[EventResponse]

}
