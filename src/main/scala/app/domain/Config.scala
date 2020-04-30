package app.domain

import app.domain.AppConfig._
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.EndsWith

sealed trait AppConfig
object AppConfig {
  type PrinceUrl       = String Refined EndsWith[W.`"little_prince.txt"`.T]
  type ClientIdsUrl    = String Refined EndsWith[W.`"client-ids.log"`.T]
  type GithubEventsUrl = String
  type EmployeesUrl    = String Refined EndsWith[W.`"ghEmployees.txt"`.T]
}

case class Config(dataPaths: DataPaths) extends AppConfig
case class DataPaths(
    littlePrinceUrl: PrinceUrl,
    clientIds: ClientIdsUrl,
    githubEvents: GithubEventsUrl,
    employeesUrl: EmployeesUrl
) extends AppConfig
