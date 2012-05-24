import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "tssdojo"
  val appVersion = "0.0.1-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    javascriptEntryPoints <<= baseDirectory(base =>
      base / "app" / "assets" / "javascripts" / "main" / "compiled" ** "*.js"
    )
  )

}
