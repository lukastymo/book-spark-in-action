package ch02

import modules.SparkApp

object Example01_RDD extends SparkApp {
  def hasPrince(x: String) = x.toLowerCase.contains("prince")

  def main(args: Array[String]): Unit = {

    val file = sc.textFile(appConfig.dataPaths.littlePrinceUrl.value)
    println(file.count())

    val princeLines = file.filter(hasPrince)
    println(princeLines.count())
  }
}
