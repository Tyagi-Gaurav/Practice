name := "SBTTest"

version := "1.0"

lazy val root = (project in file("."))
  .settings(
      name := "SBTTest",
      version := "1.0",
      scalaVersion := "2.10.2",
      libraryDependencies += "junit" % "junit" % "4.11",
      libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.3"
)

