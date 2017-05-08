name := """Algos"""


version := "1.0.0"

scalaVersion := "2.11.3"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

fork := true

//Enabling Agents in Code

//lazy val agentRun = project in file(".") enablePlugins JavaAgent
//javaAgents += "com.github.jbellis" % "jamm" % "0.3.1" % "runtime"

javaOptions in run += "-agentlib:hprof=heap=sites,cpu=times"
