/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api.models.response;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the system information
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

public class SystemInfo {

    public SystemInfo() {

    }

    @SerializedName("BuildingID")
    public Integer buildingID;

    @SerializedName("Firmware_Ver")
    public String firmwareVersion;

    @SerializedName("Gateway_SN")
    public String gatewaySN;

    @SerializedName("RegistrationCompleteFlag")
    public Boolean registrationCompleteFlag;

    @SerializedName("Status")
    public String status;

    @SerializedName("SystemID")
    public Integer systemID;

    @SerializedName("System_Name")
    public String systemName;

    private ZonesStatus zonesStatus;

    public ZonesStatus getZonesStatus() {
        return zonesStatus;
    }

    public void setZonesStatus(ZonesStatus zonesStatus) {
        this.zonesStatus = zonesStatus;
    }

    private GatewayInfo gatewayInfo;

    public GatewayInfo getGatewayInfo() {
        return gatewayInfo;
    }

    public void setGetewayInfo(GatewayInfo gatewayInfo) {
        this.gatewayInfo = gatewayInfo;
    }

    private GatewaysAlerts gatewaysAlerts;

    public GatewaysAlerts getGatewaysAlerts() {
        return gatewaysAlerts;
    }

    public void setGetewaysAlerts(GatewaysAlerts gatewaysAlerts) {
        this.gatewaysAlerts = gatewaysAlerts;
    }

    public boolean hasActiveFaults() { // Always return false, implemented for compatibility and future use.
        return false;
    }

    public String getActiveFault() { // Not used, don't know status values
        return status;
    }
}
