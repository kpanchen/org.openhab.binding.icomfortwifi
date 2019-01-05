/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.handler;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.icomfortwifi.internal.api.models.v1.response.SystemInfo;

/**
 * Handler for a temperature control system. Gets and sets global system mode.
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class iComfortWiFiTemperatureControlSystemHandler extends BaseiComfortWiFiHandler {
    private SystemInfo systemInfo;

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
            // updateState(EvohomeBindingConstants.DISPLAY_SYSTEM_MODE_CHANNEL, //Moved to zone status
            // new StringType(tcsStatus.getMode().getMode()));
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command == RefreshType.REFRESH) {
            update(systemInfo);
            // } else if (channelUID.getId().equals(iComfortWiFiBindingConstants.DISPLAY_SYSTEM_MODE_CHANNEL)) { //Mode
            // channel moved to Zone handler
            // iComfortWiFiBridgeHandler bridge = getiComfortWiFiBridge();
            // if (bridge != null) {
            // bridge.setTcsMode(getiComfortWiFiThingConfig().id, command.toString());
            // }
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
