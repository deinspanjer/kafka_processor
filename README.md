# Kafka Processor
==================

A project that shows how to write a simple producer and consumer in Scala.

## Requirements
1. Kafka 0.8.2.2+
2. Sbt 13.9+
3. Scala 2.10.6

## Producer
------------
The producer expects a topic name as command-line argument, and reads the result of 'ps aux'
command from the standard input.
It will parse the results and insert the timestamp along with an array of objects with json
as:
```
{
    "pid": <process_id>
    "command": <command>
}
```


## Consumer
------------
Consumer reads a stream of messages from the given topic (command-line argument), and 
displays the timestamp along with the count of processes. The consumer will continue to
print out the messages until stopped.


## Building & Running
----------------------
1. To run the producer:
    `
        sbt "run-main com.qburst.main.ProducerMain <topic_name_" < ps aux
    `
2.  To run the consumer:
    `
        sbt "run-main com.qburst.main.ConsumerMain topic_name"
    `   