package com.qbrainx.model

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props, SupervisorStrategy}
import com.qbrainx.service.StudentDataBaseImpl._
import spray.json._
import com.qbrainx.model.StudentImplicits._
import scala.concurrent.duration.DurationInt
import scala.io.StdIn

case object ReceiveJson

class MyActor extends Actor with ActorLogging{

  val child: ActorRef =context.actorOf(Props[MyChild],"myChild")

  override def preStart(): Unit = {
    println(s"${self.path}-actor started")
  }

  override def postStop(): Unit = {
    println(s"${self.path}actor is stopped")
  }

  override def receive: Receive = {
    case ReceiveJson =>
      val string: String = StdIn.readLine()
      child ! string
      context.become(receiveStudentState)
    case _=>println("invalid case")
  }
  def receiveStudentState:Receive={
    case student:Student=>
      println("received student")
      log.info(s"$student")
      insert(student)
      context.unbecome()
    case _=>println("invalid case")
  }


  override val supervisorStrategy: SupervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Resume
      case _: Exception                => Escalate
    }
}

class MyChild extends Actor with ActorLogging {

  override def receive: Receive = {
    case msg: String =>
      val student = msg.parseJson.convertTo[Student]
      log.info(s"$student")
      insert(student)

    case _=>println("Invalid cannot be inserted to db")
  }
}
