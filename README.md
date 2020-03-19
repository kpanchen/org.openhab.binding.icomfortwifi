# iComfort WiFi Binding

_This is a Open HAB 2 Binding for Lennox iComfort WiFi system / thermostat (Note this will not work with newer system S30/E30)._

## Supported Things

This binding supports two things:<br />
1. Thermostat display (currently under development, only Alarm Channels currently supported for the thing)<br />
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

Thing "Thermostat display" - no configuration required.

```
Thing thermostat thing_id "Demo thermostat name" @ "Demo thermostat location" [id = "your_gateway_id", name = "Thermostat Name"]
```

## Channels

Alarm_Description - Description of the current alarm (Known valuse "Filter 1", "Humidifier Pad") (Read Only)<br />
Alarm_Code - Code of the alarm (Read Only)<br />
Alarm_Type - Type of the alarm (Read Only)<br />
Alarm_Status - Status of the alarm (Read Only)<br />
Alarm_DateTimeSet - Date and Time alarm was set (Read Only)<br />
Alarm_Number - Curent alarm number to display (up to 20) (Read / Write)<br />

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
UnifiedOperationMode - Similar to OperationMode, compatible with Google Assistant (Read / Write)<br />
AwayMode - Current away status (Read / Write)<br />
FanMode - Current fan mode (Read / Write)<br />
CoolSetPoint - Cool set point for the zone (Read / Write)<br />
HeatSetPoint - Heat set point for the zone (Read / Write)<br />
SetPoint - Heat or Cool set point for the zone (Read / Write)<br />


## Full Example

Thing example:

```
Bridge icomfortwifi:account:demoaccount "My Acoount" @ "My House" [ userName = "johndoe", password = "supersecret", refreshInterval = 60] {
    Thing zone home_zone_1 "Zone 1" @ "Whole House" [ id = "WS12A34567_0", name = "Main Zone" ]
    Thing thermostat thermostat_1 "My Thermostat" @ "Whole House" [id = "WS12A34567", name = "My Thermostat"]
}
```

Items example:

```
//Zone items
Number:Temperature Thermostat_Temperature "Temperature [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:Temperature"}
Number:Dimensionless Thermostat_Humudity "Humidity [%.1f %unit%]" <humidity> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:Humidity"}
String Thermostat_Status "System Status [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:SystemStatus"}
String Thermostat_Mode "Operation Mode [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:OperationMode"}
String Thermostat_Away_Mode "Away Mode [%s]" <heating> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:AwayMode"}
String Thermostat_Fan_Mode "Fan Mode [%s]" <fan> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:FanMode"}
Number:Temperature Thermostat_Cool_Point    "Cool Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:CoolSetPoint"}
Number:Temperature Thermostat_Heat_Point    "Heat Set Point [%.1f %unit%]" <temperature> (gWholeHouse) {channel="icomfortwifi:zone:demoaccount:home_zone_1:HeatSetPoint"}
```

```
//Thermostat items:
String Alarm_Description "Alarm Description [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#AlarmDescription"}
Number Alarm_Code "Alarm Code [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#AlarmNbr"}
String Alarm_Type "Alarm Type [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#AlarmType"}
String Alarm_Status "Alarm Status [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#AlarmStatus"}
String Alarm_DateTimeSet "Alarm Date Time Set [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#DateTimeSet"}
Number Alarm_Number "Alarm Number [%s]" <alarm> (gWholeHouse) {channel="icomfortwifi:thermostat:demoaccount:thermostat_1:alertsAndReminders#AlertNumber"}
```

Sitemap example:

```
Text item=Thermostat_Temperature
Text item=Thermostat_Humudity
Text item=Thermostat_Status
Selection  item=Thermostat_Mode mappings=[IDLE=System is idle", HEATING="System is heating", COOLING="System is cooling", WAITING="System is waiting", EMERGENCY_HEAT="System is emergency heating"]
Switch item=Thermostat_Away_Mode mappings=[AWAY_ON="Away", AWAY_OFF="Not Away"]
Selection  item=Thermostat_Fan_Mode mappings=[AUTO="Auto", ON="On", CIRCULATE="Circulate"]
Setpoint item=Thermostat_Cool_Point
Setpoint item=Thermostat_Heat_Point

Text item=Alarm_Description
Text item=Alarm_Code
Text item=Alarm_Type
Text item=Alarm_Status
Text item=Alarm_DateTimeSet

Setpoint item=Alarm_Number
```

Items example for Google Assistant (for more details see Google Assistant for OpenHAB 2 documentation):

```
Group gHomeThermostatGA "House Thermostat" { ga="Thermostat" [modes="off,heat,cool,heatcool,eco", roomHint="Living Room"] } 
  String Thermostate_Mode_GA "Thermostat Mode" (gHomeThermostatGA) {ga="thermostatMode", channel="icomfortwifi:zone:demoaccount:home_zone_1:UnifiedOperationMode"} //Available modes listed on the Group item
  Number Thermostate_Temp_GA "House Temperature" (gHomeThermostatGA) {ga="thermostatTemperatureAmbient", channel="icomfortwifi:zone:demoaccount:home_zone_1:Temperature"}
  Number Thermostate_Humid_GA "House Humidity" (gHomeThermostatGA) {ga="thermostatHumidityAmbient", channel="icomfortwifi:zone:demoaccount:home_zone_1:Humidity"} //Won't be shown in Google Home App, but will be return with voice responce
  Number Thermostate_Setpoint_GA "House Setpoint" (gHomeThermostatGA) {ga="thermostatTemperatureSetpoint", channel="icomfortwifi:zone:demoaccount:home_zone_1:SetPoint"} //Temperature Set Point for Cool or Heat mode  
  Number Thermostate_Setpoint_High_GA "House Setpoint High" (gHomeThermostatGA) {ga="thermostatTemperatureSetpointHigh", channel="icomfortwifi:zone:demoaccount:home_zone_1::CoolSetPoint"} //Temperature high (cooling) set point in auto-select Cool/Heat mode
  Number Thermostate_Setpoint_Low_GA "House Setpoint Low" (gHomeThermostatGA) {ga="thermostatTemperatureSetpointLow", channel="icomfortwifi:zone:demoaccount:home_zone_1::HeatSetPoint"} //Temperature low (heating) set point in auto-select Cool/Heat mode
```

## Foot note!

This binding is based on Nest binding and EVO Home binding, all the credits for original code goes to the original authors.<br />
I also used a lot of reversed engineering done by other programmers on Internet and I have to Thank them here!
