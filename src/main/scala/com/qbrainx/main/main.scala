package com.qbrainx.main
import akka.actor.SupervisorStrategy.Stop
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import com.qbrainx.model.{MyActor, ReceiveJson, Student}

object main extends App {
  val system=ActorSystem("myActorSystem")
  val actorJson: ActorRef =system.actorOf(Props[MyActor],"myActor")
  actorJson ! ReceiveJson
  actorJson ! Student("aakash",12,33)
  Thread.sleep(1000)
  system.terminate()

}
