package com.gt.lf.domain.app

import spray.json._
import fommil.sjs.FamilyFormats._

sealed trait appDomain;

case class Winner(id : String, value : String) extends appDomain;
