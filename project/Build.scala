import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "social-underliner"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"com.typesafe.akka" % "akka-testkit" % "2.0"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
