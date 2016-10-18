import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._
import org.fluentlenium.core.filter._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication{
      route(FakeRequest(GET, "/boum")) must beNone
    }

    "render the index page" in new WithApplication{
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
    }
	
	"render the index page in a browser" in new WithBrowser {
		 browser.goTo("http://localhost:" + port)
		 browser.$("title").getTexts().get(0) must equalTo("Login Page")
		 
		 //Check Menu
		 browser.find("#menu") must have size(1)
		 browser.find("#menu").find("li") must have size(3)
		 browser.find("#menu").find("li",0).find("a").getTexts().get(0) must equalTo("Home")
		 browser.find("#menu").find("li",1).find("a").getTexts().get(0) must equalTo("View Users")
		 browser.find("#menu").find("li",2).find("a").getTexts().get(0) must equalTo("View Organisations")
		 
		 //Check Body
		 browser.find(".body").find("form") must have size(1)
	}
  }
}
