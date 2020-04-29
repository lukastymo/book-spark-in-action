package ch01

import org.apache.spark.{ SparkConf, SparkContext }

object Example01_Intro extends App {
  val filePath = "/Users/lukasz/dev/stuff/book-spark-in-action/src/main/scala/ch01/Example01_Intro.scala"

  val conf = new SparkConf().setAppName("SparkExample").setMaster("local[*]")
  val sc   = new SparkContext(conf)

  val lines = sc.textFile(filePath)

  val words = lines.flatMap(x => x.split(" "))

  val pairs = words.map(x => (x, 1))

  val wordCount = pairs.reduceByKey((a, b) => a + b)

  wordCount.collect().foreach(println)
}
