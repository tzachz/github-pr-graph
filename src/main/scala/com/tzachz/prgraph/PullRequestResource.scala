package com.tzachz.prgraph

import com.sun.jersey.api.client.{Client, GenericType, WebResource}
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter

import scala.annotation.tailrec
import scala.collection.JavaConverters._

/**
  * Created by tzachz on 8/30/17
  */
class PullRequestResource(credentials: Credentials) {

  import PullRequestResource._

  private val client: Client = Client.create
  client.addFilter(new HTTPBasicAuthFilter(credentials.user, credentials.password))

  def getPullRequests(repo: String, org: String): Seq[GHPullRequest] = {
    val resource = client.resource("https://api.github.com")
      .path("repos")
      .path(org)
      .path(repo)
      .path("pulls")
      .queryParam("status", "open")

    readPages(resource, new GenericType[java.util.List[GHPullRequest]]() {})
  }

  private def readPages[T](resource: WebResource, resultType: GenericType[java.util.List[T]]): Seq[T] = {

    @tailrec
    def readPage(pageNumber: Int, pageSize: Int, acc: Seq[T]): Seq[T] = {
      println(s"Reading page $pageNumber from $resource")
      val events = resource.queryParam("page", Integer.toString(pageNumber)).get(resultType).asScala

      if (events.nonEmpty && events.size >= pageSize && pageNumber < MAX_PAGES) {
        readPage(pageNumber + 1, events.length, acc ++ events)
      } else {
        acc ++ events
      }
    }

    readPage(1, -1, Nil)
  }

}

object PullRequestResource {
  private val MAX_PAGES = 100
}
