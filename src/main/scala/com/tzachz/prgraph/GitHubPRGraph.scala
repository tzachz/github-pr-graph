package com.tzachz.prgraph

import java.nio.file.Paths

/**
  * Created by tzachz on 8/30/17
  */
object GitHubPRGraph extends App {

  val usage = """
    Usage: ORG REPO [-token TOKEN] [-u USER -p PASSWORD] [-k KEYWORD]

    NOTE: either supply a valid token, or valid user and password; If both are supplied, only token is used
  """

  if (args.length < 3) println(usage)
  type OptionMap = Map[Symbol, String]

  def nextOption(map : OptionMap, list: List[String]) : OptionMap = {
    def isSwitch(s : String) = s.startsWith("-")

    list match {
      case Nil => map
      case org :: repo :: tail if !isSwitch(repo) && !isSwitch(org) => nextOption(map ++ Map('repo -> repo, 'org -> org), tail)
      case "-token" :: value :: tail => nextOption(map ++ Map('token -> value), tail)
      case "-u" :: value :: tail => nextOption(map ++ Map('user -> value), tail)
      case "-p" :: value :: tail => nextOption(map ++ Map('password -> value), tail)
      case "-k" :: value :: tail => nextOption(map ++ Map('keyword -> value), tail)
      case option :: tail => println("Unknown option " + option); map
    }
  }

  val options = nextOption(Map(), args.toList)
  val credentials = Credentials(options)

  val keywordFilter: GHPullRequest => Boolean = pr => {
    options.get('keyword).forall(k => pr.branch.branchName.toLowerCase.contains(k) || pr.toString.toLowerCase.contains(k))
  }

  val prs: Seq[GHPullRequest] = new PullRequestResource(credentials)
    .getPullRequests(options('repo), options('org))
    .filter(keywordFilter)

  new DOTFileWriter(Paths.get(s"${options('org)}-${options('repo)}-PR-graph.dot"))
    .writeFile[GHPullRequest](prs, _.baseBranch.branchName, _.branch.branchName)

}
