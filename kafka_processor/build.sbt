name := "kafka_producer"

version := "1.0"

scalaVersion := "2.10.6"

val kafkaVersion = "0.8.2.2"
val json4sVersion = "3.3.0"
val scalaLoggingVersion = "2.1.2"

val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging-slf4j" % scalaLoggingVersion
val json4s = "org.json4s" %% "json4s-jackson" % json4sVersion
val kafka = "org.apache.kafka" %% "kafka" % kafkaVersion exclude("log4j", "log4j") exclude("org.slf4j","slf4j-log4j12")

libraryDependencies ++= Seq(scalaLogging, kafka, json4s)
libraryDependencies += "org.slf4j" % "log4j-over-slf4j" % "1.7.5"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"
