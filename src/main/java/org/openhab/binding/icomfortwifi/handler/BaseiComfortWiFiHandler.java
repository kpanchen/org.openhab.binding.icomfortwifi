/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.handler;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.library.types.DateTimeType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.icomfortwifi.internal.api.models.response.SystemsInfo;
import org.openhab.binding.icomfortwifi.internal.configuration.iComfortWiFiThingConfiguration;

/**
 * Base class for an iComfortWiFi handler
 *
 * @author Konstantin Panchenko - Initial contribution
 */
public abstract class BaseiComfortWiFiHandler extends BaseThingHandler {
    private iComfortWiFiThingConfiguration configuration;

    public BaseiComfortWiFiHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        configuration = getConfigAs(iComfortWiFiThingConfiguration.class);
        checkConfig();
    }

    @Override
    public void dispose() {
        configuration = null;
    }

    public String getId() {
        if (configuration != null) {
            return configuration.id;
        }
        return null;
    }

    /**
     * Returns the configuration of the Thing
     *
     * @return The parsed configuration or null
     */
    protected iComfortWiFiThingConfiguration getiComfortWiFiThingConfig() {
        return configuration;
    }

    /**
     * Retrieves the bridge
     *
     * @return The iComfortWiFi bridge
     */
    protected iComfortWiFiBridgeHandler getiComfortWiFiBridge() {
        Bridge bridge = getBridge();
        if (bridge != null) {
            return (iComfortWiFiBridgeHandler) bridge.getHandler();
        }

        return null;
    }

    /**
     * Retrieves the iComfortWiFi configuration from the bridge
     *
     * @return The current iComfortWiFi configuration
     */
    protected SystemsInfo getiComfortWiFiSystemsInfo() {
        iComfortWiFiBridgeHandler bridge = getiComfortWiFiBridge();
        if (bridge != null) {
            return bridge.getiComfortWiFiSystemsInfo();
        }

        return null;
    }

    /**
     * Retrieves the iComfortWiFi configuration from the bridge
     *
     * @return The current iComfortWiFi configuration
     */
    protected void requestUpdate() {
        Bridge bridge = getBridge();
        if (bridge != null) {
            ((iComfortWiFiBridgeHandler) bridge).getiComfortWiFiSystemsInfo();
        }
    }

    /**
     * Updates the status of the iComfortWiFi thing when it changes
     *
     * @param newStatus The new status to update to
     */
    protected void updateiComfortWiFiThingStatus(ThingStatus newStatus) {
        updateiComfortWiFiThingStatus(newStatus, ThingStatusDetail.NONE, null);
    }

    /**
     * Updates the status of the iComfortWiFi thing when it changes
     *
     * @param newStatus The new status to update to
     * @param detail    The status detail value
     * @param message   The message to show with the status
     */
    protected void updateiComfortWiFiThingStatus(ThingStatus newStatus, ThingStatusDetail detail, String message) {
        // Prevent spamming the log file
        if (!newStatus.equals(getThing().getStatus())) {
            updateStatus(newStatus, detail, message);
        }
    }

    /**
     * Checks the configuration for validity, result is reflected in the status of the Thing
     *
     * @param configuration The configuration to check
     */
    private void checkConfig() {
        if (configuration == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
                    "Configuration is missing or corrupted");
        } else if (StringUtils.isEmpty(configuration.id)) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Id not configured");
        }
    }

    protected State getAsDateTimeTypeOrNull(@Nullable Date date) {
        if (date == null) {
            return UnDefType.NULL;
        }

        long offsetMillis = TimeZone.getDefault().getOffset(date.getTime());
        Instant instant = date.toInstant().plusMillis(offsetMillis);
        return new DateTimeType(ZonedDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId()));
    }

}
