name := "kafka-scala-example"

version := "0.1"

scalaVersion := "2.12.6"

val kafkaVersion = "1.0.1"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % kafkaVersion,
  "com.typesafe" % "config" % "1.3.2"
)

