/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api.models.request;

import org.openhab.binding.icomfortwifi.internal.api.models.response.ZoneStatus;

import com.google.gson.annotations.SerializedName;

/**
 * Request model for the setting status information
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class ReqSetAwayMode {
    @SerializedName("GatewaySN")
    public String gatewaySN = null;
    @SerializedName("ZoneNumber")
    public Integer zoneNumber = 0;
    @SerializedName("AwayMode")
    public Integer awayMode = 0;
    @SerializedName("HeatSetPoint")
    public Double heatSetPoint = null;
    @SerializedName("CoolSetPoint")
    public Double coolSetPoint = null;
    @SerializedName("FanMode")
    public Integer fanMode = 0;
    @SerializedName("TempScale")
    public String preferredTemperatureUnit = null;

    public ReqSetAwayMode() {

    }

    public ReqSetAwayMode(ZoneStatus zoneStatus) {
        this.gatewaySN = zoneStatus.gatewaySN;
        this.zoneNumber = zoneStatus.zoneNumber;
        this.awayMode = zoneStatus.awayMode.getAwayValue();
        this.heatSetPoint = zoneStatus.heatSetPoint;
        this.coolSetPoint = zoneStatus.coolSetPoint;
        this.fanMode = zoneStatus.fanMode.getFanModeValue();
        this.preferredTemperatureUnit = zoneStatus.preferredTemperatureUnit.getTempUnitsValue();
    }

}
