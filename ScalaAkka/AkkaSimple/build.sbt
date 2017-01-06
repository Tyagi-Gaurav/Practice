name := "AkkaRest"

version := "1.0"

organization := "com.gt.ts"

libraryDependencies ++= {
  val akkaVersion = "2.4.16"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion
  )
}
