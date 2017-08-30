package com.tzachz.prgraph

/**
  * Created by tzachz on 8/30/17
  */
sealed trait Credentials {
  def user: String
  def password: String
}

object Credentials {
  def apply(inputOptions: Map[Symbol, String]): Credentials = {
    inputOptions.get('token)
      .map(t => OAuthCredentials(t))
      .getOrElse(UserPasswordCredentials(inputOptions('user), inputOptions('password)))
  }
}

case class UserPasswordCredentials(override val user: String, override val password: String) extends Credentials

case class OAuthCredentials(token: String) extends Credentials {
  def user: String = token
  def password = "x-oauth-basic"
}
