/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.icomfortwifi;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link iComfortWiFiBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
@NonNullByDefault
public class iComfortWiFiBindingConstants {

    private static final String BINDING_ID = "icomfortwifi";

    /** The JSON content type used when talking to iComfort. */
    public static final String JSON_CONTENT_TYPE = "application/json";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_ICOMFORT_ACCOUNT = new ThingTypeUID(BINDING_ID, "account");
    public static final ThingTypeUID THING_TYPE_ICOMFORT_THERMOSTAT = new ThingTypeUID(BINDING_ID, "thermostat");
    public static final ThingTypeUID THING_TYPE_ICOMFORT_ZONE = new ThingTypeUID(BINDING_ID, "zone");

    // List of all Channel IDs
    // Read-Only channels
    public static final String ZONE_TEMPERATURE_CHANNEL = "Temperature";
    public static final String ZONE_HUMIDITY_CHANNEL = "Humidity";
    public static final String ZONE_SYSTEM_STATUS_CHANNEL = "SystemStatus";
    // Read-Write channels
    public static final String ZONE_OPERATION_MODE_CHANNEL = "OperationMode";
    public static final String ZONE_AWAY_MODE_CHANNEL = "AwayMode";
    public static final String ZONE_UNIFIED_OPERATION_MODE_CHANNEL = "UnifiedOperationMode"; // Used to set combined
                                                                                             // Away and Operation mode
    public static final String ZONE_FAN_MODE_CHANNEL = "FanMode";
    public static final String ZONE_COOL_SET_POINT_CHANNEL = "CoolSetPoint";
    public static final String ZONE_HEAT_SET_POINT_CHANNEL = "HeatSetPoint";
    public static final String ZONE_SET_POINT_CHANNEL = "SetPoint"; // To set heat only / cool only point

    // TCS Channels
    public static final String TCS_ALARM_DESCRIPTION_CHANNEL = "alertsAndReminders#AlarmDescription";
    public static final String TCS_ALARM_NBR_CHANNEL = "alertsAndReminders#AlarmNbr";
    public static final String TCS_ALARM_TYPE_CHANNEL = "alertsAndReminders#AlarmType";
    public static final String TCS_ALARM_STATUS_CHANNEL = "alertsAndReminders#AlarmStatus";
    public static final String TCS_ALARM_DATE_TIME_SET_CHANNEL = "alertsAndReminders#DateTimeSet";
    public static final String TCS_ALARM_ALERT_NUMBER = "alertsAndReminders#AlertNumber";

    // TCS Properties
    public static final String TCS_PROPERTY_SYSTEM_NAME = "systemName";
    public static final String TCS_PROPERTY_GATEWAY_SN = "gatewaySerialNumber";
    public static final String TCS_PROPERTY_FIRMWARE_VERSION = "firmwareVersion";

    // List of Discovery properties
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";

    // List of all addressable things in OH = SUPPORTED_DEVICE_THING_TYPES_UIDS + the virtual bridge
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections.unmodifiableSet(
            Stream.of(THING_TYPE_ICOMFORT_ACCOUNT, THING_TYPE_ICOMFORT_THERMOSTAT, THING_TYPE_ICOMFORT_ZONE)
                    .collect(Collectors.toSet()));

}
