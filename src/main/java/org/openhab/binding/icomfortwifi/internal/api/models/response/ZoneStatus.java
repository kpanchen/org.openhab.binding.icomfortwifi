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
 * Response model for the zone status
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class ZoneStatus {

    @SerializedName("Away_Mode")
    public AwayStatus awayMode;

    @SerializedName("Central_Zoned_Away")
    public Integer centralZonedAway;

    @SerializedName("ConnectionStatus")
    public String connectionStatus;

    @SerializedName("Cool_Set_Point")
    public Double coolSetPoint;

    @SerializedName("DateTime_Mark")
    public String dateTimeMark;

    @SerializedName("Fan_Mode")
    public FanMode fanMode;

    @SerializedName("GMT_To_Local")
    public Integer gmtToLocal;

    @SerializedName("GatewaySN")
    public String gatewaySN;

    @SerializedName("Golden_Table_Updated")
    public Boolean goldenTableUpdated;

    @SerializedName("Heat_Set_Point")
    public Double heatSetPoint;

    @SerializedName("Indoor_Humidity")
    public Integer indoorHumidity;

    @SerializedName("Indoor_Temp")
    public Double indoorTemp;

    @SerializedName("Operation_Mode")
    public OperationMode operationMode;

    @SerializedName("Pref_Temp_Units")
    public TempUnits prefTempUnits;

    @SerializedName("Program_Schedule_Mode")
    public String programScheduleMode;

    @SerializedName("Program_Schedule_Selection")
    public Integer programScheduleSelection;

    @SerializedName("System_Status")
    public SystemStatus systemStatus;

    @SerializedName("Zone_Enabled")
    public Integer zoneEnabled;

    @SerializedName("Zone_Name")
    public String zoneName;

    @SerializedName("Zone_Number")
    public Integer zoneNumber;

    @SerializedName("Zones_Installed")
    public Integer zonesInstalled;

    public ZoneStatus() {

    }

    public boolean hasActiveFaults() { // Always false, don't know what System Status could be.
        return false;
    }

    public String getActiveFault() { // For future implementation System Status must be provided as string message
        return systemStatus.toString();
    }

    public String getZoneID() {
        return gatewaySN + "_" + zoneNumber.toString();
    }

    public enum OperationMode {
        @SerializedName("2")
        COOL_ONLY(2),
        @SerializedName("1")
        HEAT_ONLY(1),
        @SerializedName("3")
        HEAT_OR_COOL(3),
        @SerializedName("0")
        OFF(0),
        UNKNOWN(-1);

        private Integer operationModeValue;

        private OperationMode(Integer operationModeValue) {
            this.operationModeValue = operationModeValue;
        }

        public Integer getOperationModeValue() {
            return this.operationModeValue;
        }
    }

    public enum SystemStatus {
        @SerializedName("0")
        IDLE(0),
        @SerializedName("1")
        HEATING(1),
        @SerializedName("2")
        COOLING(2),
        @SerializedName("3")
        WAITING(3),
        @SerializedName("4")
        EMERGENCY_HEAT(4),
        UNKNOWN(-1);

        private Integer systemStatusValue;

        private SystemStatus(Integer systemStatusValue) {
            this.systemStatusValue = systemStatusValue;
        }

        public Integer getSystemStatusValue() {
            return this.systemStatusValue;
        }
    }

    public enum AwayStatus {
        @SerializedName("0")
        AWAY_OFF(0),
        @SerializedName("1")
        AWAY_ON(1),
        UNKNOWN(-1);

        private Integer awayValue;

        private AwayStatus(Integer awayValue) {
            this.awayValue = awayValue;
        }

        public Integer getAwayValue() {
            return this.awayValue;
        }
    }

    public enum FanMode {
        @SerializedName("0")
        AUTO(0),
        @SerializedName("1")
        ON(1),
        @SerializedName("2")
        CIRCULATE(2),
        UNKNOWN(-1);

        private Integer fanModeValue;

        private FanMode(Integer fanModeValue) {
            this.fanModeValue = fanModeValue;
        }

        public Integer getFanModeValue() {
            return this.fanModeValue;
        }
    }

    public enum TempUnits {
        @SerializedName("0")
        FAHRENHEIT("0"),
        @SerializedName("1")
        CELSIUS("1"),
        UNKNOWN("unknown");

        private String tempUnitsValue;

        private TempUnits(String tempUnitsValue) {
            this.tempUnitsValue = tempUnitsValue;
        }

        public String getTempUnitsValue() {
            return this.tempUnitsValue;
        }
    }

}
