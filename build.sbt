ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "exampleeee"

lazy val root = (project in file("."))
  .settings(
    name := "Spark in Action",
    mainClass in Compile := Some("ch03.Example02_Aggregate2"),
    libraryDependencies ++= Seq(
          "org.apache.spark"           %% "spark-sql"          % "2.4.5" % Provided,
          "com.typesafe.scala-logging" %% "scala-logging"      % "3.9.2",
          "ch.qos.logback"              % "logback-classic"    % "1.2.3",
          "com.github.pureconfig"      %% "pureconfig"         % "0.12.3",
          "io.estatico"                %% "newtype"            % "0.4.3",
          "eu.timepit"                 %% "refined"            % "0.9.10",
          "eu.timepit"                 %% "refined-pureconfig" % "0.9.10",
          "org.typelevel"              %% "cats-core"          % "2.1.1",
          "org.typelevel"              %% "cats-effect"        % "2.1.1"
        )
  )

// META-INF discarding
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}
