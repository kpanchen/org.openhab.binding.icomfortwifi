# iComfort WiFi Binding

_This is a Open HAB 2 Binding for Lennox iComfort WiFi system / thermostat (Note this will not work with newer system S30/E30)._

_(Some additional resources will be provided later)_

## Supported Things

This binding supports two things:<br />
1. Thermostat display (currently under development, no Channels currently supported for the thing, Building and Owner information planned)<br />
2. Heating / Cooling Zone (multiple zones supported).


## Discovery

All the Gateway systems and zones can be discovered automatically upon binding initialization and listed as things.

## Binding Configuration

No binding configuration is required.

## Thing Configuration

Configuration of the Bridge thing is required:

Username - your iComfortWiFi username used to logon to online or mobile system (https://www.myicomfort.com).<br />
Password - your iComfortWiFi password.

Optional refresh time, default set to 30 seconds.

```
Bridge icomfortwifi:account:demoaccount "Demo name" @ "Demo location" [ userName = "yourusername", password = "yourpassword", refreshInterval = 60] {}
```

Thing "Thermostat display" - no configuration required (currently unsupported).

Thing "Zone" - no configuration required, can be added manually or through PaperUI.<br />
For manual addition you need to know your Gateway ID:

```
Thing zone thing_id "Demo zone name" @ "Demo zone location" [ id = "your_zone_id", name = "Main Zone" ]
```

Zone ID is a combination of Gateway ID and particular Zone ID in the way "Gateway ID_Zone ID", it is constructed automatically and you can get it from PaperUI.

## Channels

Temperature - Current zone temperature (Read Only)<br />
Humidity - Current zone humidity (Read Only)<br />
SystemStatus - Current system status (Read Only)<br />
OperationMode - Current operation mode (Read / Write)<br />
AwayMode - Current away status (Read / Write)(Note: this is currently under development and requires testing)<br />
FanMode - Current fan mode (Read / Write)<br />
CoolSetPoint - Cool set point for the zone (Read / Write)<br />
HeatSetPoint - Heat set point for the zone (Read / Write)<br />


## Full Example

Thing example:

```
Bridge icomfortwifi:account:demoaccount "My Acoount" @ "My House" [ userName = "johndoe", password = "supersecret", refreshInterval = 60] {<br />
    Thing zone home_zone_1 "Zone 1" @ "Whole House" [ id = "WS12A34567_0", name = "Main Zone" ]<br />
}<br />
```

Items example:

//Zone items<br />
Number:Temperature Thermostat_Temperature "Temperature [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:Temperature"}<br />
Number:Dimensionless Thermostat_Humudity "Humidity [%.1f %unit%]" <humidity> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:Humidity"}<br />
String Thermostat_Status "System Status [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:SystemStatus"}<br />
String Thermostat_Mode "Operation Mode [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:OperationMode"}<br />
String Thermostat_Away_Mode "Away Mode [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:AwayMode"}<br />
String Thermostat_Fan_Mode "Fan Mode [%s]" <fan> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:FanMode"}<br />
Number:Temperature Thermostat_Cool_Point    "Cool Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:CoolSetPoint"}<br />
Number:Temperature Thermostat_Heat_Point    "Heat Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:HeatSetPoint"}<br />


## Foot note!

This binding is based on Nest binding and EVO Home binding, all the credits for original code goes to the original authors.<br />
I also used a lot of reversed engineering done by other programmers on Internet and I have to Thank them here!
