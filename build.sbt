import Dependencies._

ThisBuild / scalaVersion     := "3.2.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "fuzzy-search-api"
  )

// https://mvnrepository.com/artifact/dev.zio/zio-http
libraryDependencies += "dev.zio" %% "zio-http" % "0.0.4"
// https://mvnrepository.com/artifact/dev.zio/zio-json
libraryDependencies += "dev.zio" %% "zio-json" % "0.4.2"

