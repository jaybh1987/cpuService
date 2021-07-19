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
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import ch.megard.akka.http.cors.scaladsl.settings.CorsSettings

import scala.concurrent.duration._
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import graph.{Graph, OsHelper}



object HttpServerRoutingMinimal {

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
              .tick(2.seconds, 2.seconds, NotUsed)
              .map(_ => OsHelper.cpuLoad)
              .map(cpuLoad => ServerSentEvent(cpuLoad.toString))
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