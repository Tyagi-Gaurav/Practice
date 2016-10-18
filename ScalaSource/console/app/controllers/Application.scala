package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import models.User
import services.UserDataService

object Application extends Controller {
  val dataService = new UserDataService

  val loginForm = Form(
	mapping(
	"UserName" -> text(minLength = 5, maxLength = 25),
	"Password" -> text(minLength = 8, maxLength = 15)
	) 
	(User.apply) (User.unapply)
	verifying ("Username and password do not match", value => dataService.verifyCredentials(value))
	)
  )
  
  // Above
  // Alternatively you can provide constraints at the level of the overall form. These differ from constraints seen so far 
  // because they are ad-hoc - in other words, they are not named and thus unattached from any particular input field.
  

  def index = Action {
    Ok(views.html.login(loginForm))
  }
  
  def login = Action { implicit request =>
	  loginForm.bindFromRequest.fold(
		formWithErrors => BadRequest(views.html.login(formWithErrors)),
		value => 
			Ok(views.html.showUserDetails(value)).withSession(
				request.session + ("user.loggedin" -> value.userName)
			)
	  )
   }
}