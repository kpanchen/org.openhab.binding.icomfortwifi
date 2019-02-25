package org.openhab.binding.icomfortwifi.internal.api.models.response;

import javax.measure.Unit;
import javax.measure.quantity.Temperature;

import org.eclipse.smarthome.core.library.unit.ImperialUnits;
import org.eclipse.smarthome.core.library.unit.SIUnits;

import com.google.gson.annotations.SerializedName;

public class CustomTypes {

    public enum RequestStatus {
        @SerializedName("SUCCESS")
        SUCCESS,
        @SerializedName("FAILURE")
        FAILURE,
        UNKNOWN;
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

        public Unit<Temperature> getTemperatureUnit() {
            switch (this.tempUnitsValue) {
                case "0":
                    return ImperialUnits.FAHRENHEIT;
                case "1":
                    return SIUnits.CELSIUS;
                default:
                    return null;
            }
        }
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

    public enum ConnectionStatus {
        @SerializedName("GOOD")
        GOOD,
        @SerializedName("BAD")
        BAD,
        UNKNOWN;
    }

    public enum PrefferedLanguage {
        @SerializedName("0")
        ENGLISH(0),
        @SerializedName("1")
        FRENCH(1),
        @SerializedName("2")
        SPANISH(2),
        UNKNOWN(-1);

        private Integer prefferedLanguage;

        private PrefferedLanguage(Integer prefferedLanguage) {
            this.prefferedLanguage = prefferedLanguage;
        }

        public Integer getPrefferedLanguageValue() {
            return this.prefferedLanguage;
        }
    }

}
