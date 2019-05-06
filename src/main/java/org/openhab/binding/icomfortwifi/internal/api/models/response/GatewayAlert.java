package org.openhab.binding.icomfortwifi.internal.api.models.response;

import java.util.Date;

import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.AlertStatus;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the System Alert
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class GatewayAlert {

    @SerializedName("Alarm_Description")
    public String alarmDescription;

    @SerializedName("Alarm_Nbr")
    public Integer alarmNbr;

    @SerializedName("Alarm_Type")
    public String alarmType;

    @SerializedName("Alarm_Value")
    public String alarmValue;

    @SerializedName("DateTime_Reset")
    public Date dateTimeReset;

    @SerializedName("DateTime_Set")
    public Date dateTimeSet;

    @SerializedName("Status")
    public AlertStatus status;

    public GatewayAlert() {

    }

}
