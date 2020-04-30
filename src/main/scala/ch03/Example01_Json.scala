package ch03

import modules.SparkApp

object Example01_Json extends SparkApp {
  private val firstFile = "2015-03-01-0.json"

  val events = sqlContext.read.json(appConfig.dataPaths.githubEvents + firstFile)

  val pushEvents = events.filter("type = 'PushEvent'")

  unsafeLogger.info(events.count.toString)
  unsafeLogger.info(pushEvents.count.toString)

  events.show()
}
