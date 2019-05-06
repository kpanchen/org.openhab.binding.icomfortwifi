package org.openhab.binding.icomfortwifi.internal.api.models.response;

/**
 * Response model for the System Alerts
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

import java.util.ArrayList;

import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.RequestStatus;

import com.google.gson.annotations.SerializedName;

public class GatewaysAlerts {

    @SerializedName("ReturnStatus")
    public RequestStatus returnStatus;

    @SerializedName("Alerts")
    public ArrayList<GatewayAlert> systemAlert;

    public GatewaysAlerts() {

    }

}
