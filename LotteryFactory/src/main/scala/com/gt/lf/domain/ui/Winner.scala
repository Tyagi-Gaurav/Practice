package com.gt.lf.domain.ui

sealed trait Winning

case class Winner(id : String, name : String) extends Winning

object WinnerDecorator {
  def decorate(w : com.gt.lf.repo.Winner) : Winner =
    Winner(w.id, w.value)
}
