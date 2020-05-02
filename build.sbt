ThisBuild / scalaVersion := "2.11.12"

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

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
          "eu.timepit"                 %% "refined"            % "0.9.12",
          "eu.timepit"                 %% "refined-pureconfig" % "0.9.12",
          "org.typelevel"              %% "cats-core"          % "2.0.0",
          "org.typelevel"              %% "cats-effect"        % "2.0.0"
        )
  )

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
  case PathList("META-INF", "LICENSE")     => MergeStrategy.discard
  case PathList("META-INF", "NOTICE")      => MergeStrategy.discard
  case PathList("rootdoc.txt")             => MergeStrategy.discard
  case _                                   => MergeStrategy.deduplicate
}

assemblyShadeRules in assembly := Seq(ShadeRule.rename("shapeless.**" -> "new_shapeless.@1").inAll)
