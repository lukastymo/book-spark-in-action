package ch03

import modules.SparkApp

import scala.io.Source

object Example02_Aggregate extends SparkApp {
  private val firstFile = "2015-03-01-0.json"

  import sqlContext.implicits._

  val events = sqlContext.read.json(appConfig.dataPaths.githubEvents + firstFile)

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

    filtered.show(5)
  } finally src.close()

}
