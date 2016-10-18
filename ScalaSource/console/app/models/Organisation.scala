package models

import DatabaseClient._
import play.api.Logger

case class Organisation(name : String) {}

object Organisation {
   val organisationModel = mongoDBInstance("organisation")
   
   def getAllOrganisations() : List[Organisation] = {
	   Logger.info("Inside getAllOrganisations")
       val organisationDocList = organisationModel.find()
       val organisation = for (doc <- organisationDocList) 
						yield { Organisation(doc.get("name").asInstanceOf[String]) }
       organisation.toList	   
	}
}