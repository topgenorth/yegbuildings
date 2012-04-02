# YEG Buildings

## About
This is just a simple little Android application to show the various historical buildings in the city of Edmonton, Alberta, Canada. This app utilizes the City of [Edmonton's Open Data](http://data.edmonton.ca) feed for [historical buildings](http://data.edmonton.ca/Facilities-and-Structures/Historical-Buildings/prfy-5m97) as the underlying source for the buildings.  Hopefully in the future other sources of information can be utilized.

## Google maps

The default.keystore I use for development is in the util directory.

    keytool -list -alias androiddebugkey -keystore debug.keystore -storepass android -keypass android
    androiddebugkey, Nov 3, 2011, PrivateKeyEntry, 
    Certificate fingerprint (MD5): 1F:98:26:DF:F3:A5:B9:AD:0E:92:28:9B:00:82:D7:11

Here is the Android Maps API key for this development / debugging keystore: 
  
    0U7qxP9sDmYSOxxQFKVQjWOn9XDSARAhf5Ouy3A




[Tom Opgenorth](http://www.opgenorth.net)

Twitter: [@topgenorth](http://www.twitter.com/topgenorth)

## Locations for Testing
If you want to set the emulator to a given location (for testing), here are some points around Edmonton:

* Intersection of 100 Ave & 105 Street: 53.538860 -113.501118
* 109 Street & Jasper Ave 53.541111 -113.508838
* Jasper ave & 101 Street : 53.54270 -113.49332
* The Cenotaph in front of City Hall: 53.544643 -113.49006
