package controllers

import play.api.mvc._

trait Authenticated {
	def isAuthenticated[A](request : Request[A]) : Boolean = {
		request.session.get("user.loggedin") match {
			case Some(_) => true
			case None => false
		}
	}
}