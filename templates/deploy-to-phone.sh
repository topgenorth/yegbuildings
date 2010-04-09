#/bin/sh

# use this to deploy the application to your phone.  Just replace <DEVICENAME> with the name of your phone.
adb -s <DEVICENAME> uninstall net.opgenorth.yeg
adb -s <DEVICENAME> install ./HistoricalBuildings.apk
