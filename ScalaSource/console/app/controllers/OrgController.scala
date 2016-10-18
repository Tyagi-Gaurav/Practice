package controllers

import play.api._
import play.api.mvc._
import models.Organisation._

object OrgController extends Controller with Authenticated {
	def listIssuers = Action { implicit request =>
	  isAuthenticated(request) match {
		case false => Redirect(routes.Application.index)
		case true => val orgList = getAllOrganisations
				 Ok(views.html.listOrganisations(orgList))
	  }	  
	}
}