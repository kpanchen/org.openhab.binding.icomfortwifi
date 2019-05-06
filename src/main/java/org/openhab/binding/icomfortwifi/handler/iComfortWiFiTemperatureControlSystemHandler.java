/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.handler;

import java.util.Map;

import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.icomfortwifi.iComfortWiFiBindingConstants;
import org.openhab.binding.icomfortwifi.internal.api.models.response.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for a temperature control system. Gets and sets global system mode.
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class iComfortWiFiTemperatureControlSystemHandler extends BaseiComfortWiFiHandler {
    private final Logger logger = LoggerFactory.getLogger(iComfortWiFiTemperatureControlSystemHandler.class);
    private SystemInfo systemInfo;
    private Integer alertNumber = 0;

    public iComfortWiFiTemperatureControlSystemHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;

        if (systemInfo == null) {
            updateiComfortWiFiThingStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Status not found, check the display id");
        } else if (handleActiveFaults(systemInfo) == false) {
            updateiComfortWiFiThingStatus(ThingStatus.ONLINE);
            setDeviceProperties(systemInfo);

            updateState(iComfortWiFiBindingConstants.TCS_ALARM_DESCRIPTION_CHANNEL, new StringType(
                    systemInfo.getGatewaysAlerts().systemAlert.get(alertNumber).alarmDescription.toString()));

            updateState(iComfortWiFiBindingConstants.TCS_ALARM_NBR_CHANNEL,
                    new DecimalType(systemInfo.getGatewaysAlerts().systemAlert.get(alertNumber).alarmNbr));

            updateState(iComfortWiFiBindingConstants.TCS_ALARM_TYPE_CHANNEL,
                    new StringType(systemInfo.getGatewaysAlerts().systemAlert.get(alertNumber).alarmType.toString()));

            // Set Alarm status
            updateState(iComfortWiFiBindingConstants.TCS_ALARM_STATUS_CHANNEL,
                    new StringType(systemInfo.getGatewaysAlerts().systemAlert.get(alertNumber).status.toString()));

            updateState(iComfortWiFiBindingConstants.TCS_ALARM_DATE_TIME_SET_CHANNEL,
                    getAsDateTimeTypeOrNull(systemInfo.getGatewaysAlerts().systemAlert.get(alertNumber).dateTimeSet));

            updateState(iComfortWiFiBindingConstants.TCS_ALARM_ALERT_NUMBER, new DecimalType(alertNumber));
        }
    }

    private void setDeviceProperties(SystemInfo systemInfo) {
        Map<String, String> properties = editProperties();
        properties.put(iComfortWiFiBindingConstants.TCS_PROPERTY_SYSTEM_NAME, systemInfo.systemName);
        properties.put(iComfortWiFiBindingConstants.TCS_PROPERTY_GATEWAY_SN, systemInfo.gatewaySN);
        properties.put(iComfortWiFiBindingConstants.TCS_PROPERTY_FIRMWARE_VERSION, systemInfo.firmwareVersion);
        updateProperties(properties);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Entering TCS Handler for Gateway {}", systemInfo.gatewaySN);
        logger.debug("Executing command {}", command.toString());
        if (command == RefreshType.REFRESH) {
            update(systemInfo);
        } else if (channelUID.getId().equals(iComfortWiFiBindingConstants.TCS_ALARM_ALERT_NUMBER)
                && command instanceof DecimalType) {
            // Handling Alert Number to display
            alertNumber = ((DecimalType) command).intValue();
            update(systemInfo);
        }
    }

    private boolean handleActiveFaults(SystemInfo systemInfo) { // Not handling at the moment, don't know values for
                                                                // status
        if (systemInfo.hasActiveFaults()) {
            updateiComfortWiFiThingStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    systemInfo.getActiveFault());
            return true;
        }
        return false;
    }

}
