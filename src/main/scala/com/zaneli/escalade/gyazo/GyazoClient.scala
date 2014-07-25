package com.zaneli.escalade.gyazo

import com.zaneli.escalade.gyazo.model.{ ImagesList, Image }
import eu.medsea.mimeutil.MimeUtil
import java.io.{ File, FileInputStream }
import java.nio.file.Files
import org.json4s.{ DefaultFormats, JString }
import org.json4s.native.JsonMethods.parse
import scalaj.http.{ Http, HttpOptions, MultiPart }
import scalaj.http.Http.Request

class GyazoClient(accessToken: String) {

  private[this]type StatusCode = Int
  private[this]type ETag = String

  private[this] val apiHost = "api.gyazo.com"
  private[this] val uploadHost = "upload.gyazo.com"

  private[this] val options = List(HttpOptions.connTimeout(5000), HttpOptions.readTimeout(5000))

  private[this] var cachedImages: Option[(ETag, List[Image])] = None

  private[this] val tokenHeader = ("Authorization", "Bearer " + accessToken)
  private[this] def etagHeader(etag: String) = ("If-None-Match", etag)

  MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector")

  def list(page: Int = 1, perPage: Int = 20): ImagesList = {
    val params = Map("page" -> page, "per_page" -> perPage)
    val (code, headers, body) = cachedImages match {
      case Some((etag, _)) => exec("images", params, "get", tokenHeader, etagHeader(etag))
      case _ => exec("images", params, "get", tokenHeader)
    }
    if (code == 304) {
      val res = ImagesList(headers)
      res.copy(images = cachedImages.map(_._2).getOrElse(Nil))
    } else {
      val res = ImagesList(headers, parse(body))
      cachedImages = getHeaderValue(headers, "ETag", identity).map(etag => (etag, res.images))
      res
    }
  }

  def upload(image: File): Image = {
    val (_, _, body) = multipart("upload", "imagedata", image, tokenHeader)
    Image(parse(body))
  }

  def delete(imageId: String): (String, String) = {
    implicit val formats = DefaultFormats
    val (_, _, body) = exec(s"images/${imageId}", Map(), "delete", tokenHeader)
    val value = parse(body)
    val JString(deletedId) = value \ "image_id"
    val JString(deletedImageType) = value \ "type"
    (deletedId, deletedImageType)
  }

  private[this] def exec(
    path: String, params: Map[String, Any], method: String, hs: (String, String)*): (StatusCode, Map[String, List[String]], String) = {
    doExec(Http(mkUrl(apiHost, path)).params(params.map { case (k, v) => (k, v.toString) }), method, hs: _*)
  }

  private[this] def multipart(
    path: String, name: String, file: File, hs: (String, String)*): (StatusCode, Map[String, List[String]], String) = {
    val req = Http.multipart(mkUrl(uploadHost, path), MultiPart(name, file.getName, getMimeType(file), Files.readAllBytes(file.toPath)))
    doExec(req, "post", hs: _*)
  }

  private[this] def doExec(req: Request, method: String, hs: (String, String)*): (StatusCode, Map[String, List[String]], String) = {
    req.method(method).options(options).headers(hs: _*).asHeadersAndParse(Http.readString)
  }

  private[this] def mkUrl(host: String, path: String): String = s"https://${host}/api/${path}"

  private[this] def getMimeType(file: File): String = {
    import scala.collection.JavaConversions._
    MimeUtil.getMimeTypes(file).head.toString
  }
}
