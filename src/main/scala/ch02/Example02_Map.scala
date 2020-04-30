package ch02

import modules.SparkApp

object Example02_Map extends SparkApp {
  val numbers = sc.parallelize(10 to 100 by 10)

  val squared = numbers.map(x => x * x)

  unsafeLogger.info(squared.collect.mkString(","))
}
