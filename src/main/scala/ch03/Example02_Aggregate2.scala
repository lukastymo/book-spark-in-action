package ch03

import cats.Eval
import eu.timepit.refined.{ W, _ }
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.Or
import eu.timepit.refined.string.EndsWith
import modules.SparkApp
import org.apache.spark.sql.{ Dataset, Row, SaveMode }
import cats.effect.{ IO, Sync }

import scala.io.Source

object Example02_Aggregate2 extends SparkApp {
  private val filesMask = "*.json"

  type OutputFileExtension = (EndsWith[W.`".json"`.T] Or EndsWith[W.`".parquet"`.T])
  type OutputFile          = String Refined OutputFileExtension

  if (args.length > 0) unsafeLogger.info(s"Output: ${args(0)}")

  val target: OutputFile = refineV[OutputFileExtension](if (args.length < 1) "" else args(0))
    .getOrElse(sys.error("Not provided or supported output extension"))

  //.getOrElse(sys.error("Incorrect output format. Supported: json or parquet only"))

  import sqlContext.implicits._

  val events = sqlContext.read.json(appConfig.dataPaths.githubEvents + filesMask)

  val pushEvents = events.filter("type = 'PushEvent'")

  val pushesByLogin          = pushEvents.groupBy("actor.login")
  val pushesByLoginCount     = pushesByLogin.count
  val pushesByLoginCountDesc = pushesByLoginCount.orderBy(pushesByLoginCount("count").desc)

  pushesByLoginCountDesc.show(5)

  val src = Source.fromFile(appConfig.dataPaths.employeesUrl.value)
  try {
    val employees = src.getLines().map(_.trim).toSet
    unsafeLogger.info(employees.mkString(","))

    val bcEmployees   = sc.broadcast(employees)
    val isEmployee    = (user: String) => bcEmployees.value.contains(user)
    val isEmployeeUDF = sqlContext.udf.register("isEmployeeUDF", isEmployee)
    val filtered      = pushesByLoginCountDesc.filter(isEmployeeUDF('login))

    program(filtered).unsafeRunSync()
  } finally src.close()

  def program(filtered: Dataset[Row]): IO[Either[String, Unit]] =
    Sync[IO].delay {
      if (target.value.endsWith("json"))
        Right(filtered.write.format("json").mode(SaveMode.Overwrite).save(target.value))
      else if (target.value.endsWith("parquet"))
        Right(filtered.write.format("parquet").mode(SaveMode.Overwrite).save(target.value))
      else
        Left("Unsupported extension")
    }

}
