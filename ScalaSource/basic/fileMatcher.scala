object fileMatcher {
       private def filesHere = (java.io.File(".")).listFiles

       private def fileMatching(matcher : String => boolean) {
       	       for (file <- filesHere ; if matcher(file))
	       	   yield file
       }

       def fileEnding(query : String) = {
       	   fileMatching(_.endsWith(query))
       }
}