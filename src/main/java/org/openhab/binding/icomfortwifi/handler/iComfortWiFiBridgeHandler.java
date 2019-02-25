/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.icomfortwifi.RunnableWithTimeout;
import org.openhab.binding.icomfortwifi.internal.api.iComfortWiFiApiClient;
import org.openhab.binding.icomfortwifi.internal.api.models.response.SystemInfo;
import org.openhab.binding.icomfortwifi.internal.api.models.response.SystemsInfo;
import org.openhab.binding.icomfortwifi.internal.api.models.response.ZoneStatus;
import org.openhab.binding.icomfortwifi.internal.configuration.iComfortWiFiBridgeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This bridge handler connects to iComfort WiFi and handles all the API requests. It pulls down the
 * updated data, polls the system and does all the co-ordination with the other handlers
 * to get the data updated to the correct things.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
@NonNullByDefault
public class iComfortWiFiBridgeHandler extends BaseBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(iComfortWiFiBridgeHandler.class);
    private final HttpClient httpClient;
    private @NonNullByDefault({}) iComfortWiFiBridgeConfiguration configuration;
    private @NonNullByDefault({}) iComfortWiFiApiClient apiClient;
    private List<iComfortWiFiAccountStatusListener> listeners = new CopyOnWriteArrayList<iComfortWiFiAccountStatusListener>();

    protected @Nullable ScheduledFuture<?> refreshTask;

    public iComfortWiFiBridgeHandler(Bridge thing, HttpClient httpClient) {
        super(thing);
        this.httpClient = httpClient;
    }

    @Override
    public void initialize() {
        configuration = getConfigAs(iComfortWiFiBridgeConfiguration.class);

        if (checkConfig()) {
            try {
                apiClient = new iComfortWiFiApiClient(configuration, this.httpClient);
            } catch (Exception e) {
                logger.error("Could not start API client", e);
                updateAccountStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                        "Could not create iComfortWiFi API client");
            }

            if (apiClient != null) {
                // Initialization can take a while, so kick it off on a separate thread
                scheduler.schedule(() -> {
                    if (apiClient.login()) {
                        startRefreshTask();
                    } else {
                        updateAccountStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                                "Authentication failed");
                    }
                }, 0, TimeUnit.SECONDS);
            }
        }
    }

    @Override
    public void dispose() {
        disposeRefreshTask();
        disposeApiClient();
        listeners.clear();
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
    }

    public SystemsInfo getiComfortWiFiSystemsInfo() {
        return apiClient.getSystemsInfo();
    }

    public void setZoneOperationMode(ZoneStatus zoneStatus, Integer mode) {
        tryToCall(() -> apiClient.setZoneOperationMode(zoneStatus, mode));
        updateThings();
    }

    public void setZoneFanMode(ZoneStatus zoneStatus, Integer mode) {
        tryToCall(() -> apiClient.setZoneFanMode(zoneStatus, mode));
        updateThings();
    }

    public void setZoneAwayMode(ZoneStatus zoneStatus, Integer mode) {
        tryToCall(() -> apiClient.setZoneAwayMode(zoneStatus, mode));
        updateThings();
    }

    // Set zone cool point command
    public void setZoneCoolingPoint(ZoneStatus zoneStatus, double doubleValue) {
        // Validate that desired temperature in the range of gateway set points
        for (SystemInfo systemInfo : apiClient.getSystemsInfo().systemInfo) {
            if (systemInfo.gatewaySN.equals(zoneStatus.gatewaySN)) {
                if (doubleValue >= systemInfo.getGatewayInfo().coolSetPointLowLimit
                        && doubleValue <= systemInfo.getGatewayInfo().coolSetPointHighLimit) {
                    tryToCall(() -> apiClient.setZoneCoolingPoint(zoneStatus, doubleValue));
                    break;
                }
            }

        }
        updateThings();

    }

    // Set zone heat point command
    public void setZoneHeatingPoint(ZoneStatus zoneStatus, double doubleValue) {
        // Validate that desired temperature in the range of gateway set points
        for (SystemInfo systemInfo : apiClient.getSystemsInfo().systemInfo) {
            if (systemInfo.gatewaySN.equals(zoneStatus.gatewaySN)) {
                if (doubleValue >= systemInfo.getGatewayInfo().heatSetPointLowLimit
                        && doubleValue <= systemInfo.getGatewayInfo().heatSetPointHighLimit) {
                    tryToCall(() -> apiClient.setZoneHeatingPoint(zoneStatus, doubleValue));
                    break;
                }
            }

        }
        updateThings();
    }

    public void addAccountStatusListener(iComfortWiFiAccountStatusListener listener) {
        listeners.add(listener);
        listener.accountStatusChanged(getThing().getStatus());
    }

    public void removeAccountStatusListener(iComfortWiFiAccountStatusListener listener) {
        listeners.remove(listener);
    }

    private void tryToCall(RunnableWithTimeout action) {
        try {
            action.run();
        } catch (TimeoutException e) {
            updateAccountStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Timeout on executing request");
        }
    }

    private void disposeApiClient() {
        if (apiClient != null) {
            apiClient.logout();
        }
        apiClient = null;
    }

    private void disposeRefreshTask() {
        if (refreshTask != null) {
            refreshTask.cancel(true);
        }
    }

    private boolean checkConfig() {
        String errorMessage = "";

        if (StringUtils.isEmpty(configuration.userName)) {
            errorMessage = "Username not configured";
        } else if (StringUtils.isEmpty(configuration.password)) {
            errorMessage = "Password not configured";
        } else {
            return true;
        }

        updateAccountStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, errorMessage);
        return false;
    }

    private void startRefreshTask() {
        disposeRefreshTask();

        refreshTask = scheduler.scheduleWithFixedDelay(this::update, 0, configuration.refreshInterval,
                TimeUnit.SECONDS);
    }

    private void update() {
        try {
            apiClient.update();
            updateAccountStatus(ThingStatus.ONLINE);
            updateThings();
        } catch (Exception e) {
            updateAccountStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, e.getMessage());
            logger.debug("Failed to update system status", e);
        }
    }

    private void updateAccountStatus(ThingStatus newStatus) {
        updateAccountStatus(newStatus, ThingStatusDetail.NONE, null);
    }

    private void updateAccountStatus(ThingStatus newStatus, ThingStatusDetail detail, @Nullable String message) {
        // Prevent spamming the log file
        if (!newStatus.equals(getThing().getStatus())) {
            updateStatus(newStatus, detail, message);
            updateListeners(newStatus);
        }
    }

    private void updateListeners(ThingStatus status) {
        for (iComfortWiFiAccountStatusListener listener : listeners) {
            listener.accountStatusChanged(status);
        }
    }

    private void updateThings() {
        Map<String, SystemInfo> idToTcsMap = new HashMap<>();
        Map<String, ZoneStatus> idToZoneMap = new HashMap<>();
        Map<String, String> zoneIdToTcsIdMap = new HashMap<>();
        Map<String, ThingStatus> idToTcsThingsStatusMap = new HashMap<>();

        // Creating lookup tables
        for (SystemInfo systemInfo : apiClient.getSystemsInfo().systemInfo) {
            idToTcsMap.put(systemInfo.gatewaySN, systemInfo);
            for (ZoneStatus zoneStatus : systemInfo.getZonesStatus().zoneStatus) {
                idToZoneMap.put(zoneStatus.getZoneID(), zoneStatus);
                zoneIdToTcsIdMap.put(zoneStatus.getZoneID(), systemInfo.gatewaySN);
            }

        }

        // Then update the things by type, with pre-filtered info
        for (Thing thing : getThing().getThings()) {
            ThingHandler thingHandler = thing.getHandler();

            if (thingHandler instanceof iComfortWiFiTemperatureControlSystemHandler) {
                iComfortWiFiTemperatureControlSystemHandler tcsHandler = (iComfortWiFiTemperatureControlSystemHandler) thingHandler;
                tcsHandler.update(idToTcsMap.get(tcsHandler.getId()));
                idToTcsThingsStatusMap.put(tcsHandler.getId(), tcsHandler.getThing().getStatus());
            }

            if (thingHandler instanceof iComfortWiFiHeatingZoneHandler) {
                iComfortWiFiHeatingZoneHandler zoneHandler = (iComfortWiFiHeatingZoneHandler) thingHandler;
                zoneHandler.update(idToTcsThingsStatusMap.get(zoneIdToTcsIdMap.get(zoneHandler.getId())),
                        idToZoneMap.get(zoneHandler.getId()));
            }
        }
    }

}
