package com.gt.lf.domain.ui

sealed trait Winning

case class Winner(id : String) extends Winning
