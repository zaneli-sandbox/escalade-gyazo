package com.zaneli.escalade.gyazo

import com.github.nscala_time.time.Imports.DateTimeZone
import java.io.File

object TestUtil {

  private[gyazo] def getClient(client: MockClient): GyazoClient = new GyazoClient("", client)

  private[gyazo] class MockClient(body: String, header: Map[String, List[String]] = Map()) extends BaseClient {
    override def exec(
      path: String, params: Map[String, Any], method: String, hs: (String, String)*): (StatusCode, Map[String, List[String]], String) = {
      (200, header, body)
    }
    override def multipart(
      path: String, name: String, file: File, hs: (String, String)*): (StatusCode, Map[String, List[String]], String) = {
      (200, header, body)
    }
  }

  private[gyazo] val JST = DateTimeZone.forID("Asia/Tokyo")

  private[gyazo] val expectedListRes = """
[
    {
        "image_id": "8980c52421e452ac3355ca3e5cfe7a0c",
        "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c",
        "thumb_url": "https://i.gyazo.com/thumb/afaiefnaf.png",
        "url": "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png",
        "type": "png",
        "created_at": "2014-05-21T14:23:10+0900"
    },
    {
        "image_id": "8980c52421e452ac3355ca3e5cfe7a0d",
        "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d",
        "thumb_url": "https://i.gyazo.com/thumb/afaiefnag.png",
        "url": "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d.png",
        "type": "png",
        "created_at": "2014-05-21T14:23:11+0900"
    }
]"""

  private[gyazo] val expectedUploadRes = """
{
   "image_id" : "8980c52421e452ac3355ca3e5cfe7a0c",
   "permalink_url": "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c",
   "thumb_url" : "https://i.gyazo.com/thumb/180/afaiefnaf.png",
   "url" : "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png",
   "type": "png",
   "created_at": "2014-05-21T14:23:10+0900"
}"""

  private[gyazo] val expectedDeleteRes = """
{
   "image_id": "8980c52421e452ac3355ca3e5cfe7a0c",
   "type": "png"
}"""
}
