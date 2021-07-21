import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn
import akka.NotUsed
import akka.stream.scaladsl.Source
import akka.http.scaladsl.Http
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.model.sse.ServerSentEvent
import akka.http.scaladsl.server.Directives
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings

import scala.concurrent.duration._
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import graph.OsHelper
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json._

import scala.concurrent.Await
import scala.concurrent.duration._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import  jsonFormat._




object HttpServerRoutingMinimal extends Directives {

  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.executionContext

    val c = CorsSettings.defaultSettings.withAllowGenericHttpRequests(true)

    val route = cors() {
      path("events") {
        import akka.http.scaladsl.marshalling.sse.EventStreamMarshalling._
        get {
          complete {
            Source
              .tick(1.seconds, 1.seconds, NotUsed)
              .map{ _ =>
                Graph.toJson(Graph(OsHelper.cpuLoad, "0", "0"))
              }
              .map(cpuLoad => ServerSentEvent(cpuLoad))
              .keepAlive(1.second, () => ServerSentEvent.heartbeat)
          }
        }
      }
    }

    val bindingFuture = Http().newServerAt("localhost", 8181).bind(route)

    println(s"Server online at http://localhost:8181/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}