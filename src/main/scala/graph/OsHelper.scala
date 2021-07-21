package graph

import akka.http.scaladsl.server.Directives

import com.sun.management
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._
import java.lang.management.ManagementFactory

object OsHelper {
  val bean = ManagementFactory.getOperatingSystemMXBean.asInstanceOf[com.sun.management.OperatingSystemMXBean]
  def cpuLoad: String = f"${bean.getSystemCpuLoad * 100}%2.2f"
  def processCpuLoad: String = f"${bean.getProcessCpuLoad * 100}%2.2f"
  def freeSwapMemory: String = f"${bean.getFreeSwapSpaceSize * 100}%2.2f"

}













