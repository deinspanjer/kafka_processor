package com.qburst.kafka

import java.util.Properties

class KafkaConfig(val hostname: String = "kafka-server") {
  private val config = new Properties()

  config.put("metadata.broker.list", s"$hostname:9092")
  config.put("serializer.class", "kafka.serializer.DefaultEncoder")
  config.put("partitioner.class", "kafka.producer.DefaultPartitioner")
  config.put("producer.type", "sync")

  config.put("zookeeper.connect", s"$hostname:2181")
  config.put("group.id", "consumer-default")
  config.put("auto.offset.reset", "largest")
  config.put("zookeeper.session.timeout.ms", "400")
  config.put("zookeeper.sync.time.ms", "250")
  config.put("auto.commit.interval.ms", "1000")

  def getConfig: Properties = config
}
