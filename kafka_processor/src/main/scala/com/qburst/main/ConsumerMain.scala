package com.qburst.main

import com.qburst.kafka.{ConsumerListener, KafkaConfig, KafkaConsumer}
import com.qburst.models.ProcessCollection
import com.typesafe.scalalogging.slf4j.LazyLogging
import com.fasterxml.jackson.core.JsonParseException

object ConsumerMain extends App with LazyLogging {
  if(args.isEmpty || 1 != args.length) {
    println("Usage: <topic_name>")
  } else {
    val topic = args(0)
    val consumer = new KafkaConsumer(new KafkaConfig("localhost"))
    consumer.addListener("1", new ProcessListener)
    consumer.consume(topic)
    consumer.close()
  }

  class ProcessListener extends ConsumerListener {
    override def messageConsumed(topic: String, message: String): Unit = {
      try {
        val pc = ProcessCollection(message)
        logger.info(s"Timestamp: ${pc.timestamp}, Process count: ${pc.processes.size}")
      } catch {
        case ex: JsonParseException => logger.error(s"Unable to parse message. Invalid json: $message")
      }
    }
  }
}
