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
 * Response model for the user profile
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class OwnerProfileInfo {

    @SerializedName("FirstName")
    public String firstName;

    @SerializedName("LastName")
    public String lastName;

    @SerializedName("MobilePhone")
    public String mobilePhone;

    @SerializedName("NewGatewayPending")
    public Boolean newGatewayPending;

    @SerializedName("Phone")
    public String phone;

    @SerializedName("PwdFlag")
    public Boolean pwdFlag;

    @SerializedName("RegistrationComplete")
    public Boolean registrationComplete;

    @SerializedName("ReturnStatus")
    public String returnStatus;

    @SerializedName("TCInComplete")
    public Boolean tcInComplete;

    @SerializedName("UserID")
    public String userID;

    @SerializedName("eMail")
    public String eMail;

    public OwnerProfileInfo() {

    }

}