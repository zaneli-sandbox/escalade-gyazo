organization := "com.zaneli"

name := "escalade-gyazo"

version := "0.0.1"

scalaVersion := "2.11.4"

crossScalaVersions := Seq("2.10.4", "2.11.4")

scalacOptions ++= Seq("-encoding", "UTF-8", "-feature", "-deprecation", "-unchecked")

libraryDependencies ++= {
  Seq(
    "com.github.nscala-time" %% "nscala-time" % "1.6.0" % "compile",
    "eu.medsea.mimeutil" % "mime-util" % "2.1.3",
    "org.json4s" %% "json4s-native" % "3.2.11" % "compile",
    "org.scalaj" %% "scalaj-http" % "1.1.0" % "compile",
    "org.scalatest" %% "scalatest" % "2.2.3" % "test"
  )
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath + "/.m2/repository")))
