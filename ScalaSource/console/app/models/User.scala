package models

import play.api.Logger

case class User(userName : String, password:String) {
	def this(userName : String) = this(userName, "")
}

