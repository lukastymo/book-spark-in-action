package app.domain

import app.domain.AppConfig._
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.EndsWith

sealed trait AppConfig
object AppConfig {
  type PrinceUrl = String Refined EndsWith[W.`"little_prince.txt"`.T]
  type ClientIds = String Refined EndsWith[W.`"client-ids.log"`.T]
}

case class Config(dataPaths: DataPaths)                                extends AppConfig
case class DataPaths(littlePrinceUrl: PrinceUrl, clientIds: ClientIds) extends AppConfig
