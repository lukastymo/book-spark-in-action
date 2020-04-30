package modules

import org.slf4j.{ Logger, LoggerFactory }

trait Logging {
  def unsafeLogger: Logger = LoggerFactory.getLogger(getClass.getName)
}
