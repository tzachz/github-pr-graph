package com.tzachz.prgraph

import com.fasterxml.jackson.annotation.{JsonCreator, JsonIgnoreProperties, JsonProperty}

/**
  * Created by tzachz on 8/30/17
  */
@JsonIgnoreProperties(ignoreUnknown = true)
case class GHPullRequest @JsonCreator()(
                                         @JsonProperty("title") title: String,
                                         @JsonProperty("html_url") url: String,
                                         @JsonProperty("number") number: Int,
                                         @JsonProperty("user") user: GHUser,
                                         @JsonProperty("head") branch: GHBranch,
                                         @JsonProperty("base") baseBranch: GHBranch
                                       ) {
  override def toString: String = "[" + number + "] " + title.replace("\"", "'") + '\n' + url
}

@JsonIgnoreProperties(ignoreUnknown = true)
case class GHUser @JsonCreator()(@JsonProperty("login") login: String)

@JsonIgnoreProperties(ignoreUnknown = true)
case class GHBranch @JsonCreator()(@JsonProperty("ref") branchName: String)