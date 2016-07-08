package com.qburst.main

import com.qburst.kafka.{KafkaConfig, KafkaProducer}
import com.qburst.models.{ProcessCollection, ProcessObject}
import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.io.Source

object ProducerMain extends App with LazyLogging {
  if(args.isEmpty || 1 != args.length) {
    println("Usage: <topic_name>")
  } else {
    val topic = args(0)
    logger.info("Reading from standard input.")
    try {
      val processes = Source.stdin.getLines().drop(1).map(parseProcess)
      val pc = new ProcessCollection(System.currentTimeMillis(), processes.toList)
      val producer = new KafkaProducer(new KafkaConfig("localhost"))
      producer.push(topic, pc.toJson)
      producer.close()
    } catch {
      case ex: IllegalArgumentException => logger.error("Unable to parse input.")
    }
  }

  private def parseProcess(p: String) = {
    try {
      val props = p.split("\\s+")
      new ProcessObject(props(1).toInt, props(10))
    } catch {
      case ex: Throwable => throw new IllegalArgumentException()
    }
  }
}
