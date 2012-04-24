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

## Credits

Ported some code Java code from [android-mapballoonviews](https://github.com/jgilfelt/android-mapviewballoons) 
(to get the balloons for the buildings). When I get the time, maybe I can/will a "proper" port of this code to 
a Mono for Android project.

# Contact Me

Website: [Tom Opgenorth](http://www.opgenorth.net)

Twitter: [@topgenorth](http://www.twitter.com/topgenorth)

## Locations for Testing
If you want to set the emulator to a given location (for testing), here are some points around Edmonton (although you're better off avoid the emulator, IMHO):

* [Intersection of 100 Ave & 105 Street](http://maps.google.ca/maps?q=53.538860,-113.501118&ie=UTF8&hnear=0x53a0223864d091af:0x3dc00d823226c039,%2B53%C2%B0+32'+20.79%22,+-113%C2%B0+30'+4.87%22&gl=ca&t=h&z=14): 53.538860 -113.501118
* [109 Street & Jasper Ave](http://maps.google.ca/maps?q=53.541111,+-113.508838&hl=en&sll=53.539108,-113.501353&sspn=0.042489,0.08729&t=h&gl=ca&z=16) 53.541111 -113.508838
* [Jasper ave & 101 Street](http://maps.google.ca/maps?q=+53.54270,+-113.49332&hl=en&ll=53.542705,-113.493319&spn=0.010621,0.021822&sll=53.541111,-113.508838&sspn=0.010622,0.021822&t=h&gl=ca&z=16) : 53.54270 -113.49332
* [The Cenotaph in front of City Hall](http://g.co/maps/qndc4): 53.544643 -113.49006
