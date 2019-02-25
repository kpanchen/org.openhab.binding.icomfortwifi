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

import com.google.gson.annotations.SerializedName;

/**
 * Alias for a list of locations
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class BuildingsInfo {

    @SerializedName("ReturnStatus")
    public RequestStatus returnStatus;

    @SerializedName("Buildings")
    public ArrayList<Building> buildingInfo;

    public BuildingsInfo() {

    }

}