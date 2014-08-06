package com.zaneli.escalade

import scala.util.Try

package object gyazo {
  private[gyazo]type StatusCode = Int
  private[gyazo]type ETag = String

  private[gyazo] def getHeaderValue[A](
    headers: Map[String, List[String]], name: String, f: (String => A)): Option[A] =
    headers.get(name).flatMap(_.headOption).flatMap(x => Try(f(x)).toOption)
}
