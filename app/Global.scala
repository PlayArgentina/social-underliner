import play.api._
import play.api.libs.concurrent.Akka
import akka.actor.Props
import models.LoggerActor
import akka.util.duration._
import play.api.Play.current
import play.api.Logger

import models._

object Global extends GlobalSettings {
  
  override def onStart(app: Application){
    val actor = Akka.system.actorOf(Props[LoggerActor], name = "logger")
    Logger.debug(String.format("Actor from global -> %s", actor))
    // Akka.system.scheduler.schedule(0 seconds, 1 seconds) {
    //   actor ! Log("Test", "Code Mother Fucker!")
    // }
  }

}