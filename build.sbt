organization := "com.zaneli"

name := "escalade-gyazo"

version := "0.0.1"

scalaVersion := "2.11.2"

crossScalaVersions := Seq("2.10.4", "2.11.2")

scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked")

libraryDependencies ++= {
  Seq(
    "com.github.nscala-time" %% "nscala-time" % "1.2.0" % "compile",
    "eu.medsea.mimeutil" % "mime-util" % "2.1.3",
    "org.json4s" %% "json4s-native" % "3.2.10" % "compile",
    "org.scalaj" %% "scalaj-http" % "0.3.16" % "compile",
    "org.scalatest" %% "scalatest" % "2.2.1" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
