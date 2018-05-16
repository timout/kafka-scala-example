package timout.kafka.config

import java.util.Properties
import java.{lang => jl}

import com.typesafe.config.Config

import scala.collection.JavaConverters._

class KafkaConfig(cfg: Config) {

  val toMap: Map[String, String] =
    cfg.getConfig("kafka.params").entrySet().asScala.map ( p =>
      p.getValue.unwrapped match {
        case i: jl.Integer =>
          (p.getKey, i.toString )
        case b: jl.Boolean =>
          (p.getKey, b.toString )
        case a => (p.getKey, a.asInstanceOf[String])
      }
    ).toMap

  val toProperties : Properties = toMap.foldLeft( new Properties())( (p, e) => {
    p.setProperty(e._1, e._2)
    p
  })

  val topic: String = cfg.getString("kafka.topic")

}

object KafkaConfig {

  def apply(cfg: Config): KafkaConfig = new KafkaConfig(cfg)

  implicit def configToProperties(c: KafkaConfig): Properties = c.toProperties
}
