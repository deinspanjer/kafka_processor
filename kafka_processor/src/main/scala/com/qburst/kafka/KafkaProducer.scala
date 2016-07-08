package com.qburst.kafka

import com.typesafe.scalalogging.slf4j.LazyLogging
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

class KafkaProducer(config: KafkaConfig) extends LazyLogging {
  val kafkaProducerConfig = new ProducerConfig(config.getConfig)
  val producer = new Producer[Array[Byte], Array[Byte]](kafkaProducerConfig)

  /** Pushes message to given topic.
    * Note: Auto topic creation needs to be enabled on the server.
    *
    * @param topic topic to push to
    * @param message the message to push
    */
  def push(topic: String, message: String) = {
    val msg = new KeyedMessage[Array[Byte], Array[Byte]](topic, message.getBytes())
    producer.send(msg)
    logger.info(s"Pushed message to $topic.")
  }

  def close(): Unit = producer.close()
}
