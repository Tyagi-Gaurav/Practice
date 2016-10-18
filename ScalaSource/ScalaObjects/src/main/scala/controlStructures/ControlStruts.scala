package controlStructures

/**
 *
 * @author: tyagig
 */
object ControlStruts {
  def main(args : Array[String]) {
    printFilesInCurrentDir
  }

	def printFilesInCurrentDir = {
		val fileList = (new java.io.File(".")).listFiles()

		for (file <- fileList)  //Each iteration initialises a val named "file"
			println(file)
	}
}
