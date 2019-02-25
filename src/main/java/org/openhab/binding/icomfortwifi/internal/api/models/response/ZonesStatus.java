/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api.models.response;

import java.util.ArrayList;

import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.RequestStatus;

/**
 * Response model for the system status
 *
 * @author Konstantin Panchenko- Initial contribution
 *
 */

import com.google.gson.annotations.SerializedName;

public class ZonesStatus {

    @SerializedName("ReturnStatus")
    public RequestStatus returnStatus;

    @SerializedName("tStatInfo")
    public ArrayList<ZoneStatus> zoneStatus;

    public ZonesStatus() {

    }

}
