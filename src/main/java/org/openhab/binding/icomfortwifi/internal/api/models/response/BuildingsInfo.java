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

import com.google.gson.annotations.SerializedName;

/**
 * Alias for a list of locations
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class BuildingsInfo {

    // Validation request enum
    public enum Status {
        SUCCESS {
            @Override
            public String asLowerCase() {
                return SUCCESS.toString().toLowerCase();
            }
        },
        FAILURE {
            @Override
            public String asLowerCase() {
                return FAILURE.toString().toLowerCase();
            }
        };

        public abstract String asLowerCase();
    };

    @SerializedName("ReturnStatus")
    public String returnStatus;

    @SerializedName("Buildings")
    public ArrayList<Building> buildingInfo;

    public BuildingsInfo() {

    }

}