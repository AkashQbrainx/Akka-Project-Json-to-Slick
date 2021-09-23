
import sbt._
object Dependencies {
  lazy val sprayJsonVersion="1.3.6"
  lazy val slickVersion="3.3.3"
  lazy val mySqlVersion="8.0.26"
  lazy val akkaVersion= "2.6.16"
  lazy val sprayJson= "io.spray" %% "spray-json" % sprayJsonVersion
  lazy val slick= "com.typesafe.slick" %% "slick" % slickVersion
  lazy val mySqlConnector="mysql" % "mysql-connector-java" % mySqlVersion
  lazy val akka="com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val hickari="com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  def compilerDependencies=Seq(sprayJson,hickari,slick,mySqlConnector,akka)

}
