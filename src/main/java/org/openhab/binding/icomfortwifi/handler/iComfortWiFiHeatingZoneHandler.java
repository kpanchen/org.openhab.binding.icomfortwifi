/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.handler;

import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.eclipse.smarthome.core.library.types.QuantityType;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.icomfortwifi.iComfortWiFiBindingConstants;
import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.AwayStatus;
import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.FanMode;
import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.OperationMode;
import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.TempUnits;
import org.openhab.binding.icomfortwifi.internal.api.models.response.ZoneStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link iComfortWiFiHeatingZoneHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
public class iComfortWiFiHeatingZoneHandler extends BaseiComfortWiFiHandler {

    private final Logger logger = LoggerFactory.getLogger(iComfortWiFiHeatingZoneHandler.class);
    private ThingStatus tcsStatus;
    private ZoneStatus zoneStatus;

    public iComfortWiFiHeatingZoneHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    public void update(ThingStatus tcsStatus, ZoneStatus zoneStatus) {
        this.tcsStatus = tcsStatus;
        this.zoneStatus = zoneStatus;

        // Make the zone offline when the related tcs is offline
        // If the related display is not a thing, ignore this
        if (tcsStatus != null && tcsStatus.equals(ThingStatus.OFFLINE)) {
            updateiComfortWiFiThingStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Display Controller offline");
        } else if (zoneStatus == null) {
            updateiComfortWiFiThingStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Status not found, check the zone id");
        } else if (handleActiveFaults(zoneStatus) == false) {
            updateiComfortWiFiThingStatus(ThingStatus.ONLINE);

            updateState(iComfortWiFiBindingConstants.ZONE_TEMPERATURE_CHANNEL, new QuantityType<>(zoneStatus.indoorTemp,
                    zoneStatus.preferredTemperatureUnit.getTemperatureUnit()));
            updateState(iComfortWiFiBindingConstants.ZONE_HUMIDITY_CHANNEL,
                    new QuantityType<>(zoneStatus.indoorHumidity, SmartHomeUnits.PERCENT));
            updateState(iComfortWiFiBindingConstants.ZONE_SYSTEM_STATUS_CHANNEL,
                    new StringType(zoneStatus.systemStatus.toString()));
            updateState(iComfortWiFiBindingConstants.ZONE_OPERATION_MODE_CHANNEL,
                    new StringType(zoneStatus.operationMode.toString()));
            updateState(iComfortWiFiBindingConstants.ZONE_AWAY_MODE_CHANNEL,
                    new StringType(zoneStatus.awayMode.toString()));
            updateState(iComfortWiFiBindingConstants.ZONE_FAN_MODE_CHANNEL,
                    new StringType(zoneStatus.fanMode.toString()));
            updateState(iComfortWiFiBindingConstants.ZONE_COOL_SET_POINT_CHANNEL, new QuantityType<>(
                    zoneStatus.coolSetPoint, zoneStatus.preferredTemperatureUnit.getTemperatureUnit()));
            updateState(iComfortWiFiBindingConstants.ZONE_HEAT_SET_POINT_CHANNEL, new QuantityType<>(
                    zoneStatus.heatSetPoint, zoneStatus.preferredTemperatureUnit.getTemperatureUnit()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("Entering Heating Zone Handler for Gateway {}, zone {}", zoneStatus.gatewaySN,
                zoneStatus.zoneNumber);
        logger.debug("Executing command {}", command.toString());

        if (command == RefreshType.REFRESH) {
            update(tcsStatus, zoneStatus);
        } else {

            iComfortWiFiBridgeHandler bridge = getiComfortWiFiBridge();
            if (bridge != null) {
                // Accommodating the case where Framework units are different from back end.
                if (command instanceof QuantityType) {
                    Unit<Temperature> tempUnit = ((QuantityType<Temperature>) command).getUnit();
                    if (tempUnit != zoneStatus.preferredTemperatureUnit.getTemperatureUnit()) {
                        bridge.setAlternateTemperatureUnit(TempUnits.getCustomTemperatureUnit(tempUnit));
                    }
                }

                String channelId = channelUID.getId();
                if (iComfortWiFiBindingConstants.ZONE_AWAY_MODE_CHANNEL.equals(channelId)) {
                    bridge.setZoneAwayMode(zoneStatus, AwayStatus.valueOf(command.toString()).getAwayValue());
                } else if (zoneStatus.awayMode == AwayStatus.AWAY_OFF) {
                    logger.debug("Zone is not in Away mode, executing the command");
                    if (iComfortWiFiBindingConstants.ZONE_COOL_SET_POINT_CHANNEL.equals(channelId)
                            && command instanceof QuantityType) {
                        bridge.setZoneCoolingPoint(zoneStatus, ((QuantityType<Temperature>) command).doubleValue());

                    } else if (iComfortWiFiBindingConstants.ZONE_HEAT_SET_POINT_CHANNEL.equals(channelId)
                            && command instanceof QuantityType) {
                        bridge.setZoneHeatingPoint(zoneStatus, ((QuantityType<Temperature>) command).doubleValue());

                    } else if (iComfortWiFiBindingConstants.ZONE_OPERATION_MODE_CHANNEL.equals(channelId)) {
                        bridge.setZoneOperationMode(zoneStatus,
                                OperationMode.valueOf(command.toString()).getOperationModeValue());
                    } else if (iComfortWiFiBindingConstants.ZONE_FAN_MODE_CHANNEL.equals(channelId)) {
                        bridge.setZoneFanMode(zoneStatus, FanMode.valueOf(command.toString()).getFanModeValue());

                    }
                } else {
                    logger.debug("Zone is in Away mode, not executing the command");
                }
            }
        }
    }

    private boolean handleActiveFaults(ZoneStatus zoneStatus) {
        if (zoneStatus.hasActiveFaults()) {
            updateiComfortWiFiThingStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    zoneStatus.getActiveFault());
            return true;
        }
        return false;
    }

}
