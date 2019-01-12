/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.api;

import org.openhab.binding.icomfortwifi.internal.api.models.request.ReqSetAwayMode;

/**
 * List of iComfortWiFi API commands
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public final class iComfortWiFiApiCommands {

    private final static class iComfortServiceURI {
        private final class URI {
            private static final String HOST = "services.myicomfort.com";
            private static final String PORT = "443";
            private static final String PROTOCOL = "https:";

            private URI() {
            }
        }

        private static final String BASE_PATH = "/DBAcessService.svc";

        private iComfortServiceURI() {
        }

        protected String getURI() {
            // @formatter:off
            StringBuilder urlBuilder = new StringBuilder(URI.PROTOCOL)
                    .append("//")
                    .append(URI.HOST)
                    .append(":")
                    .append(URI.PORT)
                    .append(BASE_PATH);
            // @formatter:on
            return urlBuilder.toString();
        }
    }

    private final static class validateUser {
        private static final String PATH = "/ValidateUser";

        private final class paramsDef {
            private static final String USER_NAME = "UserName";
            private static final String LANGUAGE_NBR = "lang_nbr";

            private paramsDef() {
            }
        }

        private validateUser() {
        }
    }

    private final static class getOwnerProfileInfo {
        private static final String PATH = "/GetOwnerProfileInfo";

        private final class paramsDef {
            private static final String USER_ID = "userid";

            private paramsDef() {
            }
        }

        private getOwnerProfileInfo() {
        }
    }

    private final static class getBuildingsInfo {
        private static final String PATH = "/GetBuildingsInfo";

        private final class paramsDef {
            private static final String USER_ID = "userid";

            private paramsDef() {
            }
        }

        private getBuildingsInfo() {
        }
    }

    private final static class getSystemsInfo {
        private static final String PATH = "/GetSystemsInfo";

        private final class paramsDef {
            private static final String USER_ID = "userid";

            private paramsDef() {
            }
        }

        private getSystemsInfo() {
        }
    }

    private final static class getGatewayInfo {
        private static final String PATH = "/GetGatewayInfo";

        private final class paramsDef {
            private static final String GATEWAY_SN = "gatewaysn";
            private static final String TEMP_UNIT = "tempunit";

            private paramsDef() {
            }
        }

        private getGatewayInfo() {
        }
    }

    private final static class getTStatInfoList {
        private static final String PATH = "/GetTStatInfoList";

        @SuppressWarnings("unused")
        private final class paramsDef {
            private static final String GATEWAY_SN = "gatewaysn";
            private static final String TEMP_UNIT = "tempunit";
            private static final String CENTRAL_ZONED_AWAY = "Central_Zoned_Away";
            private static final String CANCEL_AWAY = "Cancel_Away";
            private static final String ZONE_NUMBER = "Zone_Number";

            private paramsDef() {
            }
        }

        private getTStatInfoList() {
        }
    }

    private final static class setAwayModeNew {
        private static final String PATH = "/SetAwayModeNew";

        @SuppressWarnings("unused")
        private final class paramsDef {
            private static final String GATEWAY_SN = "gatewaysn";
            private static final String ZONE_NUMBER = "zonenumber";
            private static final String AWAY_MODE = "awaymode";
            private static final String HEAT_SET_POINT = "heatsetpoint";
            private static final String COOL_SET_POINT = "coolsetpoint";
            private static final String FAN_MODE = "fanmode";
            private static final String TEMP_SCALE = "tempscale";

            private paramsDef() {
            }
        }

        private setAwayModeNew() {
        }
    }

    private final static class setTStatInfo {
        private static final String PATH = "/SetTStatInfo";

        @SuppressWarnings("unused")
        private final class paramsDef {

            private paramsDef() {
            }
        }

        private setTStatInfo() {
        }
    }

    public static String getCommandValidateUser(String userName, Integer lngNumber) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(validateUser.PATH)
                .append("?")
                .append(validateUser.paramsDef.USER_NAME)
                .append("=")
                .append(userName)
                .append("&")
                .append(validateUser.paramsDef.LANGUAGE_NBR)
                .append("=")
                .append(lngNumber.toString());
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandGetOwnerProfileInfo(String userName) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(getOwnerProfileInfo.PATH)
                .append("?")
                .append(getOwnerProfileInfo.paramsDef.USER_ID)
                .append("=")
                .append(userName);
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandGetBuildingsInfo(String userName) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(getBuildingsInfo.PATH)
                .append("?")
                .append(getBuildingsInfo.paramsDef.USER_ID)
                .append("=")
                .append(userName);
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandGetSystemsInfo(String userName) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(getSystemsInfo.PATH)
                .append("?")
                .append(getSystemsInfo.paramsDef.USER_ID)
                .append("=")
                .append(userName);
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandGetGatewayInfo(String gatewaySN, String tempUnit) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(getGatewayInfo.PATH)
                .append("?")
                .append(getGatewayInfo.paramsDef.GATEWAY_SN)
                .append("=")
                .append(gatewaySN)
                .append("&")
                .append(getGatewayInfo.paramsDef.TEMP_UNIT)
                .append("=")
                .append(tempUnit);
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandGetTStatInfoList(String gatewaySN, String tempUnit) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(getTStatInfoList.PATH)
                .append("?")
                .append(getTStatInfoList.paramsDef.GATEWAY_SN)
                .append("=")
                .append(gatewaySN)
                .append("&")
                .append(getTStatInfoList.paramsDef.TEMP_UNIT)
                .append("=")
                .append(tempUnit);
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandSetAwayModeNew(ReqSetAwayMode reqSetAway) {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(setAwayModeNew.PATH)
                .append("?")
                .append(setAwayModeNew.paramsDef.GATEWAY_SN)
                .append("=")
                .append(reqSetAway.gatewaySN.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.ZONE_NUMBER)
                .append("=")
                .append(reqSetAway.zoneNumber.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.AWAY_MODE)
                .append("=")
                .append(reqSetAway.awayMode.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.HEAT_SET_POINT)
                .append("=")
                .append(reqSetAway.heatSetPoint.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.COOL_SET_POINT)
                .append("=")
                .append(reqSetAway.coolSetPoint.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.FAN_MODE)
                .append("=")
                .append(reqSetAway.fanMode.toString())
                .append("&")
                .append(setAwayModeNew.paramsDef.TEMP_SCALE)
                .append("=")
                .append(reqSetAway.prefTempUnits.toString());
        // @formatter:on
        return urlBuilder.toString();
    }

    public static String getCommandSetTStatInfo() {
        // @formatter:off
        StringBuilder urlBuilder = new StringBuilder((new iComfortServiceURI()).getURI())
                .append(setTStatInfo.PATH);
        // @formatter:on
        return urlBuilder.toString();
    }

}
