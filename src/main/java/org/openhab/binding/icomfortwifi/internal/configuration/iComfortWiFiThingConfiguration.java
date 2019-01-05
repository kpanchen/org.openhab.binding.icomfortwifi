/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.configuration;

/**
 * Contains the common configuration definition of an iComfortWiFi Thing
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */
public class iComfortWiFiThingConfiguration {
    public String id = ""; // GatewaySN for System ID or GatewaySN_ZoneID for Zonde ID
    public String name = ""; // System or Zone name (optional)
}
