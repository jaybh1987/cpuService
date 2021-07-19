package graph


import com.sun.management

import java.lang.management.ManagementFactory
case class Graph(a:Double, b: Double, c: Double)


object OsHelper {
  val bean = ManagementFactory.getOperatingSystemMXBean.asInstanceOf[com.sun.management.OperatingSystemMXBean]
  def cpuLoad: Double = bean.getProcessCpuLoad
}













