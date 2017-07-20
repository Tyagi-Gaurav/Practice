package com.gt.lf.domain.ui

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

sealed trait Winning

case class Winner(id : String, name : String) extends Winning

case class Winners(winner : List[Winner]) extends Winning

object WinnerDecorator {
  def decorate(w : List[com.gt.lf.repo.Winner]) : Winners =
    Winners(w.map(x => Winner(x.id, x.value)))
}
