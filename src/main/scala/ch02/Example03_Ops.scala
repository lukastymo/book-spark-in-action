package ch02

import modules.SparkApp

object Example03_Ops extends SparkApp {
  val clientIds           = sc.textFile(appConfig.dataPaths.clientIds.value)
  val normalizedClientIds = clientIds.flatMap(_.split(",").map(_.trim()))
  val uniqueClientIds     = normalizedClientIds.map(_.toInt).distinct()

  unsafeLogger.info(uniqueClientIds.collect.mkString(","))

  val sample = uniqueClientIds.sample(withReplacement = false, 0.3)
  unsafeLogger.info(sample.collect.mkString(","))

  val approxSum = sample.sumApprox(10000L, 0.95)
  unsafeLogger.info(approxSum.toString())
}
