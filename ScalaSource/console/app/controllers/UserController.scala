package controllers

import play.api._
import play.api.mvc._
//import services.UserDataService

object UserController extends Controller with Authenticated {

   def listUsers = Action { implicit request =>
   	   isAuthenticated(request) match {
		case false => Redirect(routes.Application.index)
		case true => val userList = getAllUsers
					 Ok(views.html.listUser(userList))
		}
   }
}