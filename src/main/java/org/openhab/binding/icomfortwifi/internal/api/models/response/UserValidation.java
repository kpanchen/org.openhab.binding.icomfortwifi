/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api.models.response;

import org.openhab.binding.icomfortwifi.internal.api.models.response.CustomTypes.RequestStatus;

import com.google.gson.annotations.SerializedName;

/**
 * Response model for the authentication
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

public class UserValidation {

    @SerializedName("msg_code")
    public RequestStatus msgCode;

    @SerializedName("msg_desc")
    public String msgDesc = "";

}
