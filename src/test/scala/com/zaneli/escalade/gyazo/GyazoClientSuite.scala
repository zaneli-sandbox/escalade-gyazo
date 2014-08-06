package com.zaneli.escalade.gyazo

import com.github.nscala_time.time.Imports.DateTime
import com.zaneli.escalade.gyazo.TestUtil.{ expectedListRes, expectedUploadRes, expectedDeleteRes, getClient, JST, MockClient }
import java.io.File
import org.scalatest.FunSuite

class GyazoClientSuite extends FunSuite {

  test("get list (no params)") {
    val mock = new MockClient(expectedListRes)
    val client = getClient(mock)

    val res = client.list()
    assert(res.currentPage == 1)
    assert(res.perPage == 20)
    assert(res.images.size == 2)

    {
      val image = res.images(0)
      assert(image.imageId == "8980c52421e452ac3355ca3e5cfe7a0c")
      assert(image.permalinkUrl == "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c")
      assert(image.thumbUrl == "https://i.gyazo.com/thumb/afaiefnaf.png")
      assert(image.url == "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png")
      assert(image.imgType == "png")
      assert(image.createdAt.withZone(JST) == new DateTime(2014, 5, 21, 14, 23, 10, JST))
    }
    {
      val image = res.images(1)
      assert(image.imageId == "8980c52421e452ac3355ca3e5cfe7a0d")
      assert(image.permalinkUrl == "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d")
      assert(image.thumbUrl == "https://i.gyazo.com/thumb/afaiefnag.png")
      assert(image.url == "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d.png")
      assert(image.imgType == "png")
      assert(image.createdAt.withZone(JST) == new DateTime(2014, 5, 21, 14, 23, 11, JST))
    }
  }

  test("get list (set params)") {
    val mock = new MockClient(
      expectedListRes,
      Map("X-Total-Count" -> List("350"), "X-Current-Page" -> List("3"), "X-Per-Page" -> List("100"), "X-User-Type" -> List("lite")))
    val client = getClient(mock)

    val res = client.list(page = 3, perPage = 100)
    assert(res.currentPage == 3)
    assert(res.perPage == 100)
    assert(res.totalCount == 350)
    assert(res.userType == Some("lite"))
    assert(res.images.size == 2)

    {
      val image = res.images(0)
      assert(image.imageId == "8980c52421e452ac3355ca3e5cfe7a0c")
      assert(image.permalinkUrl == "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c")
      assert(image.thumbUrl == "https://i.gyazo.com/thumb/afaiefnaf.png")
      assert(image.url == "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png")
      assert(image.imgType == "png")
      assert(image.createdAt.withZone(JST) == new DateTime(2014, 5, 21, 14, 23, 10, JST))
    }
    {
      val image = res.images(1)
      assert(image.imageId == "8980c52421e452ac3355ca3e5cfe7a0d")
      assert(image.permalinkUrl == "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d")
      assert(image.thumbUrl == "https://i.gyazo.com/thumb/afaiefnag.png")
      assert(image.url == "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0d.png")
      assert(image.imgType == "png")
      assert(image.createdAt.withZone(JST) == new DateTime(2014, 5, 21, 14, 23, 11, JST))
    }
  }

  test("upload") {
    val mock = new MockClient(expectedUploadRes)
    val client = getClient(mock)

    val res = client.upload(new File("test.png"))
    assert(res.imageId == "8980c52421e452ac3355ca3e5cfe7a0c")
    assert(res.permalinkUrl == "http://gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c")
    assert(res.thumbUrl == "https://i.gyazo.com/thumb/180/afaiefnaf.png")
    assert(res.url == "https://i.gyazo.com/8980c52421e452ac3355ca3e5cfe7a0c.png")
    assert(res.imgType == "png")
    assert(res.createdAt.withZone(JST) == new DateTime(2014, 5, 21, 14, 23, 10, JST))
  }

  test("delete") {
    val mock = new MockClient(expectedDeleteRes)
    val client = getClient(mock)

    val res = client.delete("8980c52421e452ac3355ca3e5cfe7a0c")
    assert(res == ("8980c52421e452ac3355ca3e5cfe7a0c", "png"))
  }
}
