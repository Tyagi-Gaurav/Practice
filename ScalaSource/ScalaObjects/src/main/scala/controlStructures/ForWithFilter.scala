package controlStructures

/**
 *
 * @author: tyagig
 */
object ForWithFilter {
  def main(args : Array[String]) {
    printFilesInCurrentDirWithFilter
    produceNewCollection
  }

  def printFilesInCurrentDirWithFilter = {
    val fileList = (new java.io.File(".")).listFiles()

    for (
      file <- fileList
      if file.isFile
      if file.getName.endsWith(".scala")
    ) println(file)
  }

  def produceNewCollection = {
    val filesHere = (new java.io.File(".")).listFiles()

    val newFileList = for {
      file <- filesHere
    } yield file

    println(newFileList)
  }
}
