﻿$android_sdk_dir = "C:\android-sdk-windows";
$adb =  $android_sdk_dir + "\tools\adb.exe";
$db_location = "/data/data/net.opgenorth.yeg.buildings/databases/buildings.db";
$putty = "C:\Program Files\PuTTY\putty.exe";
$adb =  $android_sdk_dir + "\tools\adb.exe";
$db_location = "/data/data/net.opgenorth.yeg.buildings/databases/buildings.db";


function Deploy-To-Device
{
	adb -s HT92RLZ00549 uninstall net.opgenorth.yeg.buildings
	adb -s HT92RLZ00549 install ./YegBuildings.apk
}

Set-Alias adb "C:\android-sdk-windows\tools\adb.exe" # pull /data/data/net.opgenorth.net.buildings.databases.buildings.db"
Set-Alias deploy Deploy-To-Device