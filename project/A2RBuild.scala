import sbt._
import Keys._

object A2RDemo extends Build {
  val Organization = "org.h3nk3"
  val Version      = "1.0-SNAPSHOT"
  val ScalaVersion = "2.9.1"

  lazy val p = Project(
    id = "akka2remote",
    base = file("."),
    settings = defaultSettings ++ Seq(libraryDependencies ++= Dependencies.akka2remoteDependencies))

  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := Organization,
    version      := Version,
    scalaVersion := ScalaVersion,
    crossPaths   := false,
    publishArtifact in packageSrc := false,
    publishArtifact in packageDoc := false)
  
  lazy val defaultSettings = buildSettings ++ Seq(    
    resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
    scalacOptions ++= Seq("-encoding", "UTF-8", "-optimise", "-deprecation", "-unchecked"),
    javacOptions  ++= Seq("-Xlint:unchecked", "-Xlint:deprecation"),
    parallelExecution in Test := false)
}

object Dependencies {
  import Dependency._
  val akka2remoteDependencies = Seq(akkaKernel, akkaRemote, akkaActor)
}

object Dependency {
  object V {
    val Akka      = "2.0-M1"
  }

  val akkaKernel        = "com.typesafe.akka" % "akka-kernel"        % V.Akka
  val akkaRemote        = "com.typesafe.akka" % "akka-remote"        % V.Akka
  val akkaActor         = "com.typesafe.akka" % "akka-actor"         % V.Akka
}
