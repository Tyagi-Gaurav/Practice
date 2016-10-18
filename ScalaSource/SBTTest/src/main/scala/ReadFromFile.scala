import scala.io.Source

/**
 * Created by gauravt on 21/01/15.
 */
class ReadFromFile

object ReadFromFile {
  def read(fileName : String) : List[String] = {
    val fileLines = for (line <- Source.fromURL(getClass.getResource(fileName)).getLines())
      yield {line}

    fileLines.toList
   }
}