import AssemblyKeys._ // put this at the top of the file

seq(slickSettings: _*)

seq(oldLwjglSettings: _*)

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.9.1"

name := "tinygod"

organization := "org.ludumdare"

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

mainClass in oneJar := Some("org.ludumdare.tinygod.Game")

assemblySettings

mainClass in assembly := Some("org.ludumdare.tinygod.Game")