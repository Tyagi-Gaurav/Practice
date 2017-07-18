enablePlugins(JavaAppPackaging)

name := "lottery-factory"
version := "1.0"
scalaVersion := "2.11.8"

resolvers ++= Seq(
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "elasticsearch" at "https://artifacts.elastic.co/maven",
  Resolver.sonatypeRepo("public")
)

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

bashScriptExtraDefines += """addJava "-Dlog4j.configurationFile=${app_home}/../conf/log4j2.xml""""

val scalatest = "org.scalatest" %% "scalatest" % "3.0.1"

libraryDependencies ++= {
  val akkaV       = "2.4.16"
  val akkaHttpV   = "10.0.1"
  val elastic4sV  = "5.3.1"

  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-testkit" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "io.spray" %%  "spray-json" % "1.3.3",
    "com.codacy" %% "scala-consul" % "2.0.2",
    "com.fasterxml.jackson.core" % "jackson-core" % "2.6.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.1",
    "com.fasterxml.jackson.core" % "jackson-annotations" % "2.6.1",
    scalatest % "test",
    "com.github.tomakehurst" % "wiremock" % "2.5.0" % "test",
    "org.mockito" % "mockito-all" % "1.9.5" % "test",
    "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sV,
    "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sV,
    "com.sksamuel.elastic4s" %% "elastic4s-jackson" % elastic4sV,
    "com.sksamuel.elastic4s" %% "elastic4s-testkit" % elastic4sV,
    "com.github.fommil" %% "spray-json-shapeless" % "1.3.0",
    "ch.megard" %% "akka-http-cors" % "0.1.11",
    "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5",
    "org.elasticsearch.client" % "x-pack-transport" % elastic4sV,
    "org.reactivemongo" % "reactivemongo_2.11" % "0.12.5"
  )
}

// Create a default Scala style task to run with tests
lazy val testScalastyle = taskKey[Unit]("testScalastyle")
testScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Test).toTask("").value

lazy val integrationTestScalaStyle = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    libraryDependencies += scalatest % "it"
  )

Revolver.settings
