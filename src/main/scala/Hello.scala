import app.domain.Config
import pureconfig.ConfigSource
import pureconfig.generic.auto._
import eu.timepit.refined.pureconfig._

object Hello {

  def main(args: Array[String]): Unit = {
    println("Done")
    println(ConfigSource.default.load[Config])
  }

}
