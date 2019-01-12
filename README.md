# iComfort WiFi Binding

_This is a Open HAB 2 Binding for Lennox iComfort WiFi system / thermostat (Note this will not work with newer system S30/E30)._

_(Some additional resources will be provided later)_

## Supported Things

_This binding supports two things:_
_1. Thermostat display (currently under development, no Channels currently supported for the thing, Building and Owner information planned)_
_2. Heating / Cooling Zone (multiple zones supported). Each zone supports the following types:_
_Temperature - Current zone temperature (Read Only)_
_Humidity - Current zone humidity (Read Only)_
_SystemStatus - Current system status (Read Only)_
_OperationMode - Current operation mode (Read / Write)_
_AwayMode - Current away status (Read / Write)(Note: this is currently under development and requires testing)_
_FanMode - Current fan mode (Read / Write)_
_CoolSetPoint - Cool set point for the zone (Read / Write)_
_HeatSetPoint - Heat set point for the zone (Read / Write)_

## Discovery

_All the Gateway systems and zones can be discovered automatically upon binding initialization and listed as things._

## Binding Configuration

_Configuration of the Bridge required:_

Username - your iComfortWiFi username used to logon to online or mobile system (https://www.myicomfort.com).
Password - your iComfortWiFi password.

Optional refresh time, default set to 30 seconds.


## Thing Configuration

_No thing configuration is required._
_Note: I haven't test manual additon through .thing file yet, but it should work, information will be provided on later stages. Use Paper UI at the moment.

## Channels

_Temperature - Current zone temperature (Read Only)_
_Humidity - Current zone humidity (Read Only)_
_SystemStatus - Current system status (Read Only)_
_OperationMode - Current operation mode (Read / Write)_
_AwayMode - Current away status (Read / Write)(Note: this is currently under development and requires testing)_
_FanMode - Current fan mode (Read / Write)_
_CoolSetPoint - Cool set point for the zone (Read / Write)_
_HeatSetPoint - Heat set point for the zone (Read / Write)_


## Full Example

_(Will be provided)_

## Foot note!

_This binding is based on Nest binding and EVO Home binding, all the credits for original code goes to the original authors._
_I also used a lot of reversed engineering done by other programmers on Internet and I have to Thank them here!_
