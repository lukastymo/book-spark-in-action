package modules

import app.domain.Config
import org.apache.spark.{ SparkConf, SparkContext }
import pureconfig.ConfigSource
import pureconfig._
import pureconfig.generic.auto._
import eu.timepit.refined.pureconfig._
import org.apache.spark.sql.{ SQLContext, SparkSession }

trait SparkApp extends App with Logging {
  private val conf  = new SparkConf().setAppName("Spark Example").setMaster("local[*]")
  private val spark = SparkSession.builder().config(conf).getOrCreate()

  val sc: SparkContext       = spark.sparkContext
  val sqlContext: SQLContext = spark.sqlContext

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
