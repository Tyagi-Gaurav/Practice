package com.gt.lf

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.OAuth2BearerToken
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, headers}
import akka.stream.Materializer
import com.typesafe.config.Config

import scala.concurrent.Future

object RestClient {
  class RestClient(httpRequest: HttpRequest) {

    def withCredentials(credentials : OAuth2BearerToken): RestClient = {
      new RestClient(httpRequest.addHeader(headers.Authorization(credentials)))
    }

    def makeRequest(implicit actorSystem: ActorSystem, materializer : Materializer) : Future[HttpResponse]
    = Http().singleRequest(httpRequest)
  }

  def apply(endpoint : String)(implicit config : Config) = {
    val scheme = config.getString("service.http_scheme")
    val host = config.getString("service.host")
    val port = config.getString("service.port")
    val serviceUri = s"$scheme://$host:$port$endpoint"

    new RestClient(HttpRequest(uri = serviceUri))
  }
}
