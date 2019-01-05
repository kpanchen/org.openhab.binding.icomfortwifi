/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
/**
  * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.discovery;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.openhab.binding.icomfortwifi.iComfortWiFiBindingConstants;
import org.openhab.binding.icomfortwifi.handler.iComfortWiFiAccountStatusListener;
import org.openhab.binding.icomfortwifi.handler.iComfortWiFiBridgeHandler;
import org.openhab.binding.icomfortwifi.internal.api.models.v1.response.SystemInfo;
import org.openhab.binding.icomfortwifi.internal.api.models.v1.response.ZoneStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link iComfortWiFiDiscoveryService} class is capable of discovering the available data from iComfortWiFi
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class iComfortWiFiDiscoveryService extends AbstractDiscoveryService
        implements iComfortWiFiAccountStatusListener {
    private final Logger logger = LoggerFactory.getLogger(iComfortWiFiDiscoveryService.class);
    private static final int TIMEOUT = 5;

    private iComfortWiFiBridgeHandler bridge;
    private ThingUID bridgeUID;

    public iComfortWiFiDiscoveryService(iComfortWiFiBridgeHandler bridge) {
        super(iComfortWiFiBindingConstants.SUPPORTED_THING_TYPES_UIDS, TIMEOUT);

        this.bridge = bridge;
        this.bridgeUID = this.bridge.getThing().getUID();
        this.bridge.addAccountStatusListener(this);
    }

    @Override
    protected void startScan() {
        discoverDevices();
    }

    @Override
    protected void startBackgroundDiscovery() {
        discoverDevices();
    }

    @Override
    protected synchronized void stopScan() {
        super.stopScan();
        removeOlderResults(getTimestampOfLastScan());
    }

    @Override
    public void accountStatusChanged(ThingStatus status) {
        if (status == ThingStatus.ONLINE) {
            discoverDevices();
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        bridge.removeAccountStatusListener(this);
    }

    //
    private void discoverDevices() {
        if (bridge.getThing().getStatus() != ThingStatus.ONLINE) {
            logger.debug("iComfortWiFi Gateway not online, scanning postponed");
            return;
        }

        for (SystemInfo systemInfo : bridge.getiComfortWiFiSystemsInfo().systemInfo) {
            addSystemDiscoveryResult(systemInfo);
            for (ZoneStatus zone : systemInfo.getSystemStatus().zoneStatus) {
                addZoneDiscoveryResult(systemInfo.systemName, zone);
            }
        }

        stopScan();
    }

    private void addSystemDiscoveryResult(SystemInfo systemInfo) {
        String id = systemInfo.gatewaySN;
        String name = systemInfo.systemName;
        ThingUID thingUID = new ThingUID(iComfortWiFiBindingConstants.THING_TYPE_ICOMFORT_THERMOSTAT, bridgeUID, id);

        Map<String, Object> properties = new HashMap<>(2);
        properties.put(iComfortWiFiBindingConstants.PROPERTY_ID, id);
        properties.put(iComfortWiFiBindingConstants.PROPERTY_NAME, name);

        addDiscoveredThing(thingUID, properties, name);
    }

    private void addZoneDiscoveryResult(String systemName, ZoneStatus zone) {
        String zoneID = zone.getZoneID();
        String name = zone.zoneName + " (" + systemName + ")";
        ThingUID thingUID = new ThingUID(iComfortWiFiBindingConstants.THING_TYPE_ICOMFORT_ZONE, bridgeUID, zoneID);

        Map<String, Object> properties = new HashMap<>(2);
        properties.put(iComfortWiFiBindingConstants.PROPERTY_ID, zoneID.toString());
        properties.put(iComfortWiFiBindingConstants.PROPERTY_NAME, name);

        addDiscoveredThing(thingUID, properties, name);
    }

    private void addDiscoveredThing(ThingUID thingUID, Map<String, Object> properties, String displayLabel) {
        DiscoveryResult discoveryResult = DiscoveryResultBuilder.create(thingUID).withProperties(properties)
                .withBridge(bridgeUID).withLabel(displayLabel).build();
        thingDiscovered(discoveryResult);
    };

}
