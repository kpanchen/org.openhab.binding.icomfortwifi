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

Configuration of the Bridge required:

Username - your iComfortWiFi username used to logon to online or mobile system (https://www.myicomfort.com).<br />
Password - your iComfortWiFi password.

Optional refresh time, default set to 30 seconds.


## Thing Configuration

No thing configuration is required.

Note: I haven't test manual additon through .thing file yet, but it should work, information will be provided on later stages. Use Paper UI at the moment

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

_(Will be provided)_

## Foot note!

This binding is based on Nest binding and EVO Home binding, all the credits for original code goes to the original authors.<br />
I also used a lot of reversed engineering done by other programmers on Internet and I have to Thank them here!
