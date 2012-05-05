package controllers

import play.api._
import play.api.mvc._
import play.api.libs.iteratee.Iteratee
import play.api.libs.iteratee.Enumerator
import play.api.libs.concurrent._
import akka.actor._
import akka.pattern.ask
import play.api.Play.current
import models._
import akka.util.Timeout
import akka.util.duration._
import play.api.Logger
import java.lang.Throwable

object Application extends Controller {
  
  implicit val timeout = Timeout(1 second)

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
 
  def logger = WebSocket.async[String] { request =>
    
    Logger.debug("A new logger request has been received")

    // Ignores input!
    val in = Iteratee.consume[String]()
    
    val loggerActor = Akka.system.actorFor("user/logger")
    Logger.debug(String.format("Actor -> %s", loggerActor))
    (loggerActor ? Bind).asPromise.map {
      case BindOk(enumerator) => {
        Logger.debug("The binded was successfull!")
        (in, enumerator)
      }
    }
  }
}