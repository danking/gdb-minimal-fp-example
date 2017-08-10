import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies += "com.github.samtools" % "htsjdk" % "2.10.1",
    libraryDependencies += "com.intel" % "genomicsdb" % "0.6.4-proto-3.0.0-beta-1"
  )
