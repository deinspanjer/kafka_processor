package com.qburst.models

import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

case class ProcessObject(pid: Int, command: String) {
  def toJson: JValue = {
    (ProcessObject.Fields.pid -> pid) ~
    (ProcessObject.Fields.command -> command)
  }

  def toJsonString: String = compact(render(toJson))
}

case object ProcessObject {
  object Fields {
    final val pid = "pid"
    final val command = "command"
  }

  def apply(json: String): ProcessObject = {
    implicit val formats = org.json4s.DefaultFormats
    val j = parse(json)
    new ProcessObject(
      (j \ ProcessObject.Fields.pid).extract[Int],
      (j \ ProcessObject.Fields.command).extract[String]
    )
  }
}
