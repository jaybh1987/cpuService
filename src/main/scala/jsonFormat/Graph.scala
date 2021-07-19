package jsonFormat

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

//def cpuLoad: String = f"${bean.getSystemCpuLoad * 100}%2.2f"
//  def freeMemory: String = f"${bean.getFreePhysicalMemorySize}%2.2f"
//  def freeSwapMemory: String = f"${bean.getFreeSwapSpaceSize}%2.2f"

final case class Graph(cpuLoad: String, freeMem: String, freeSwap: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val graphFormat = jsonFormat3(Graph)
}