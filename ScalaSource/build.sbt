scalaVersion in ThisBuild := "2.11.8"
name := "ScalaSource"
exportJars := true

def ScalaSourceProject(name : String) : Project = (
  Project (name, file(name)).
    settings (
      version := "1.0",
      organization := "com.gt",
      scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8"),
      scalaVersion := "2.11.8"
    )
  )

lazy val algos = (ScalaSourceProject("algos").
  settings(
    javaSource in Compile := baseDirectory.value / "src/main/scala",
    javaSource in Test := baseDirectory.value / "src/test/scala"
  )
)

lazy val scalaObjects = (ScalaSourceProject("ScalaObjects").
  settings(
    javaSource in Compile := baseDirectory.value / "src/main/scala",
    javaSource in Test := baseDirectory.value / "src/test/scala"
  )
)

lazy val simulations = (ScalaSourceProject("simulations").
  settings(
    javaSource in Compile := baseDirectory.value / "src/main/scala",
    javaSource in Test := baseDirectory.value / "src/test/scala"
  )
)
