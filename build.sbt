import Dependencies._
import sbt.Keys._

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, AshScriptPlugin, DockerPlugin)
  .settings(
    organization := "jp.pigumer",
    name := "jmxclient",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.6",
    javacOptions ++= Seq("-target", "1.8"),
    libraryDependencies ++= Seq(
      cloudwatch
    ),
    dockerBaseImage := "java:8-jdk-alpine",
    mainClass in assembly := Some("jp.pigumer.monitor.Application")
  )