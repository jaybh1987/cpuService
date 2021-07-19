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
  def freeMemory: String = f"${bean.getFreePhysicalMemorySize}%2.2f"
  def freeSwapMemory: String = f"${bean.getFreeSwapSpaceSize}%2.2f"

}













