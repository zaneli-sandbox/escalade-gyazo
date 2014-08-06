#escalade-gyazo
[Gyazo](https://gyazo.com/ "Gyazo") API Scala wrapper library.

[![Build Status](https://api.travis-ci.org/zaneli/escalade-gyazo.png?branch=master)](https://travis-ci.org/zaneli/escalade-gyazo)

## Usage

### List

```
val c = new com.zaneli.escalade.gyazo.GyazoClient(<access_token>)
c.list()
c.list(page = 3, perPage = 100)
```

### Upload

```
val c = new com.zaneli.escalade.gyazo.GyazoClient(<access_token>)
c.upload(new java.io.File("test.jpg"))
```

### Delete

```
val c = new com.zaneli.escalade.gyazo.GyazoClient(<access_token>)
c.delete(<image_id>)
```
