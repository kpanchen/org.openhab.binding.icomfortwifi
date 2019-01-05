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
 * Response model for the building info
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class Building {

    @SerializedName("Addr1")
    public String address_1;

    @SerializedName("Addr2")
    public String address_2;

    @SerializedName("Age_of_Building")
    public Integer buildingAge;

    @SerializedName("BuildingAlert")
    public Boolean buildingAlert;

    @SerializedName("BuildingID")
    public Integer buildingID;

    @SerializedName("BuildingReminder")
    public Boolean buildingReminder;

    @SerializedName("BuildingSize")
    public Integer buildingSize;

    @SerializedName("BuildingStyle")
    public Integer buildingStyle;

    @SerializedName("Building_Name")
    public String buildingName;

    @SerializedName("City")
    public String city;

    @SerializedName("Country")
    public String country;

    @SerializedName("DealerAlerts_DlrWants")
    public Boolean dealerAlertsDlrWants;

    @SerializedName("DealerAlerts_OwnerAllow")
    public Boolean dealerAlertsOwnerAllow;

    @SerializedName("DealerID")
    public Integer dealerID;

    @SerializedName("DealerReminder_DlrWants")
    public Boolean dealoerReminderDlrWants;

    @SerializedName("DealerReminder_OwnerAllow")
    public Boolean dealerReminderOwnerAllow;

    @SerializedName("DealerTStatView")
    public Boolean dealerTStatView;

    @SerializedName("DefaultBuilding")
    public Boolean defaultBuilding;

    @SerializedName("Latitude")
    public Double latitude;

    @SerializedName("Longitude")
    public Double longitude;

    @SerializedName("NotificationEmail")
    public String notificationEmail;

    @SerializedName("Number_of_Bedrooms")
    public Integer numberOfBedrooms;

    @SerializedName("Number_of_Floors")
    public Integer numberOfFloors;

    @SerializedName("Number_of_Occupants")
    public Integer numberOfOccupants;

    @SerializedName("St_Prov")
    public String stateOrProvince;

    @SerializedName("UserID")
    public String userID;

    @SerializedName("UtilityCompany")
    public String utilityCompany;

    @SerializedName("ZIP_PC")
    public String zipOrPostalCode;

    public Building() {

    }

}