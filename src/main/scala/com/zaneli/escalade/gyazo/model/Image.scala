package com.zaneli.escalade.gyazo.model

import com.github.nscala_time.time.Imports.{ DateTime, DateTimeFormat }
import org.json4s.{ DefaultFormats, JValue }

case class Image(
  imageId: String, permalinkUrl: String, thumbUrl: String, url: String, imgType: String, createdAt: DateTime)

object Image {

  private[this] case class DataHolder(
    image_id: String, permalink_url: String, thumb_url: String, url: String, `type`: String, created_at: String)

  private[this] val p = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ")

  def apply(value: JValue): Image = {
    implicit val formats = DefaultFormats
    val holder = value.extract[DataHolder]
    Image(holder.image_id, holder.permalink_url, holder.thumb_url, holder.url, holder.`type`, p.parseDateTime(holder.created_at))
  }
}
