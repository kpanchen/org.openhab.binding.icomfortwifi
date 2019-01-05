/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api.models.v1.response;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the authentication
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class UserValidation {

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

    @SerializedName("msg_code")
    public String msgCode = "";

    @SerializedName("msg_desc")
    public String msgDesc = "";

}
