package services

import models.DatabaseClient._
import models.User

class UserDataService {
	val userModel = mongoDBInstance("user")
	
	def getAllUsers() : List[User] = {
	   val userDocList = userModel.find()
       val userList = for (doc <- userDocList) 
						yield { User(doc.get("userIdentifier").asInstanceOf[String], 
									 doc.get("password").asInstanceOf[String]) }
       userList.toList	   
	}
	
	def verifyCredentials(user : User) : Boolean() {
		getAllUsers().exists(x => x.userName == user.userName && x.password == user.password) 
	}
}