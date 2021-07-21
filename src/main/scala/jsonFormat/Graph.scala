package jsonFormat

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, PrettyPrinter}

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper


//def cpuLoad: String = f"${bean.getSystemCpuLoad * 100}%2.2f"
//  def freeMemory: String = f"${bean.getFreePhysicalMemorySize}%2.2f"
//  def freeSwapMemory: String = f"${bean.getFreeSwapSpaceSize}%2.2f"

case class Graph(cpuLoad: String, freeMem: String, freeSwap: String)

object Graph {
  val mapper = new ObjectMapper() with ScalaObjectMapper

  mapper.registerModule(DefaultScalaModule)

  def toJson(value: Any) = {
    mapper.writeValueAsString(value)
  }
}

//trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
//  implicit val graphFormat = jsonFormat3(Graph)
//  implicit val printer = PrettyPrinter
//  implicit val prettyPrintedFormat = jsonFormat3(Graph)
//}
























