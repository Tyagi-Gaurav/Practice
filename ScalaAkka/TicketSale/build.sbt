
name := "TicketSale"
version := "1.0"

organization := "com.gt.ts"

libraryDependencies ++= {
  val akkaVersion = "2.4.12"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core"  % "2.4.11",
    "com.typesafe.akka" %% "akka-http-experimental"  % "2.4.11"
  )
}
