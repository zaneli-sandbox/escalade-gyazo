package com.zaneli.escalade.gyazo.model

import com.zaneli.escalade.gyazo.getHeaderValue
import org.json4s.JValue

case class ImagesList(images: List[Image], totalCount: Int, currentPage: Int, perPage: Int, userType: Option[String])

object ImagesList {

  def apply(headers: Map[String, List[String]]): ImagesList = {
    val totalCount = getHeaderValue(headers, "X-Total-Count", _.toInt).getOrElse(0)
    val currentPage = getHeaderValue(headers, "X-Current-Page", _.toInt).getOrElse(1)
    val perPage = getHeaderValue(headers, "X-Per-Page", _.toInt).getOrElse(20)
    val userType = getHeaderValue(headers, "X-User-Type", identity)

    ImagesList(Nil, totalCount, currentPage, perPage, userType)
  }

  def apply(headers: Map[String, List[String]], value: JValue): ImagesList =
    apply(headers).copy(images = value.children.map(Image.apply))
}
