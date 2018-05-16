package timout.kafka

import java.util.Collections

import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import timout.kafka.config.KafkaConfig

import scala.collection.JavaConverters._

object MessageConsumer {

  def main(args: Array[String]): Unit = {

    if ( args.isEmpty ) {
      println("Missing configuration file argument")
      System.exit(-1)
    }

    val configPath = args(0)
    val config = ConfigFactory.load(configPath)
    val kafkaConfig = KafkaConfig(config)

    val consumer:KafkaConsumer[String,String] = new KafkaConsumer[String,String](kafkaConfig)

    consumer.subscribe(Collections.singletonList(kafkaConfig.topic))

    while ( true ) {
      val records = consumer.poll(100)
      for(record:ConsumerRecord[String, String] <- records.asScala){
        println(s"${record.offset} : partition=${record.partition} tm=${record.timestampType} key=${record.key} value=${record.value}")
      }
    }
  }

}
