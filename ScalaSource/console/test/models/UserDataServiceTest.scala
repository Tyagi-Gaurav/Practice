package models

import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import org.specs2.mock.mocj
import com.mongodb.casbah.MongoCollection

@RunWith(classOf[JUnitRunner])
class UserDataServiceTest extends Specification {
	"UserDataService" should {
		"return a list of all users" in {
			val mockDBInstance = mock[MongoCollection]
			
			val myUserDataService = new UserDataService() {
				override def mongoDBInstance = mockDBInstance
			}
		}
	}
}