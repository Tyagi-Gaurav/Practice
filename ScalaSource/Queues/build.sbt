name := """Queues"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))

scalaVersion := "2.11.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"

libraryDependencies += "org.mockito" % "mockito-core" % "1.10.8"



