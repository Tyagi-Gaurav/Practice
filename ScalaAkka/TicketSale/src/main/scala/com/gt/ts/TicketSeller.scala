package com.gt.ts

import akka.actor.Actor

object TicketSeller {
  def props(implicit timeout: Timeout) = Props(new TicketSeller)
  case class Add(tickets: Vector[Ticket])
  case class Buy(tickets: Int)
  case class Ticket(id : Int)
  case class Tickets(event: String,
                    entries: Vector[Ticket] = Vector.empty[Ticket])

  case object GetEvent
  case object Cancel
}

class TicketSeller extends Actor {
  import TicketSeller._

  var tickets = Vector.empty[Ticket]

  def receive = {
    case Add(newTickets) => tickets = tickets ++ newTickets
    case Buy(nrOfTickets) =>
      val entries = tickets.take(nrOfTickets).toVector
      if (entries.size >= nrOfTickets) {
        sender() ! Tickets(event, entries)
        tickets = tickets.drop(nrOfTickets)
      } else sender() ! Tickets(event)

    case GetEvent => sender() ! Some(BoxOffice.Event(event, tickets.size))
    case Cancel =>
      sender() ! Some(BoxOffice.Event(event, tickets.size))
      self ! PoisonPill
  }
}
