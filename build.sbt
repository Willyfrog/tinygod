import AssemblyKeys._ // put this at the top of the file

assemblySettings

seq(slickSettings: _*)

seq(oldLwjglSettings: _*)

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.9.1"

name := "tinygod"

organization := "org.ludumdare"

mainClass in assembly := Some("Game")

