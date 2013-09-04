last.fm reader
============

Last FM info reader.

This is an experimental project for using the Last.fm API. The request's results are cached so as long as the LastFmReader instance is breathing, there won't be unnecessary networking.

Only two services were implemented. 

  * getting the artist's bio
  * getting the list of similar artists
  
This small component is basically a CLI, but can be used in a multi-threaded environment as a component as well.
It is packed as a fat jar, containing all the necessary libraries.

CLI usage
---------
```
Usage: java -jar lastfmreader.jar -k <api_key> -m <method name> -a <artist name>
```

Two API methods can be called: 

  * [artist.getinfo](http://www.lastfm.de/api/show/artist.getInfo) with the -m bio option
  * [artist.getsimilar](http://www.lastfm.de/api/show/artist.getSimilar) with the -m similar option

There is a test API key: 37be6c106e0df038465a880c7b65b15b

Module usage
------------

Here's a sample code from the Main class:

```java
RestRequestExecutor restExecutor = new RestRequestExecutor(apiKey);
ResponseMapper mapper = new ResponseMapper();
LastFmReader reader = new LastFmReader(restExecutor, mapper);
```

Then you can use your reader instance to retrieve information.


