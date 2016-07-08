package com.qburst.kafka

import com.typesafe.scalalogging.slf4j.LazyLogging
import kafka.consumer.{Consumer, ConsumerConfig}

trait ConsumerListener {
  def messageConsumed(topic: String, message: String): Unit
}

class KafkaConsumer(config: KafkaConfig) extends LazyLogging {
  private val kafkaConsumerConfig = new ConsumerConfig(config.getConfig)
  private val consumer = Consumer.create(kafkaConsumerConfig)
  private val listeners = collection.mutable.Map[String, ConsumerListener]()

  def consume(topic: String) = {
    val consumerMap = consumer.createMessageStreams(Map(topic -> 1))
    val streams = consumerMap.get(topic).get
    logger.info(s"Beginning consumption from topic: $topic")
    streams.foreach(s => {
      val it = s.iterator()
      while(it.hasNext()) {
        for(l <- listeners.values) l.messageConsumed(topic, new String(it.next().message()))
      }
    })
  }

  def addListener(name: String, listener: ConsumerListener) = listeners(name) = listener
  def removeListener(name: String): Unit = listeners.remove(name)

  def close(): Unit = consumer.shutdown()
}
