package timout.kafka

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import timout.kafka.config.KafkaConfig

object MessageProducer {

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Missing configuration file argument")
      System.exit(-1)
    }

    val configPath = args(0)
    val config = ConfigFactory.load(configPath)
    val kafkaConfig = KafkaConfig(config)

    val producer = new KafkaProducer[String, String](kafkaConfig)
    var counter = 1L
    while (true) {
      val key = s"$counter"
      val value = s" thread=1 message=$counter"
      producer.beginTransaction()
      val record = new ProducerRecord[String, String](kafkaConfig.topic, key, value)
      producer.send(record)
      Thread.sleep(100)
      counter += 1
    }
  }

}

