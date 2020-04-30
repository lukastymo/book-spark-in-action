package modules

import app.domain.Config
import org.apache.spark.{ SparkConf, SparkContext }
import pureconfig.ConfigSource
import pureconfig._
import pureconfig.generic.auto._
import eu.timepit.refined.pureconfig._

trait SparkApp extends App with Logging {
  private val conf = new SparkConf().setAppName("Spark Example").setMaster("local[*]")

  val sc: SparkContext  = new SparkContext(conf)
  val appConfig: Config = loadConfig

  unsafeLogger.info("Started")

  private def loadConfig: Config =
    ConfigSource.default.load[Config] match {
      case Right(value) =>
        unsafeLogger.info(s"Config loaded $value")
        value
      case Left(e) => sys.error(e.toString)
    }
}
