package models
import java.io.OutputStream
import akka.actor.Actor
import scala.collection.mutable
import java.io.PrintStream
import play.api.libs.iteratee._
import play.api.Logger

sealed abstract class LoggerMessage
case class Bind() extends LoggerMessage
case class BindOk(val enumerator: Enumerator[String]) extends LoggerMessage
case class Log(val from : String, val message : String) extends LoggerMessage

class LoggerActor extends Actor {
  
  val channels = mutable.Set[PushEnumerator[String]]()
  
  def receive = {
    case Bind => {
      val channel = Enumerator.imperative[String]()
      channels += channel
      Logger.debug("New binding!")
      Logger.debug(String.format("Sender -> %s", sender))
      sender ! channel
    }
    case Log(from, message) => {
      Logger.debug(String.format("Receive from: %s, message: %s", from, message))
      channels.foreach(_.push(String.format("%s: %s", from, message)))
    }
  }
  
}