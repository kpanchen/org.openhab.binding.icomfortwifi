/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.icomfortwifi.internal.configuration;

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The configuration for the Nest bridge, allowing it to talk to Nest.
 *
 * @author Konstantin Panchenko - Initial contribution
 */
@NonNullByDefault
public class iComfortWiFiBridgeConfiguration {

    private static final Integer DEFAULT_REFRESH = 30;

    public String userName = "";
    public String password = "";

    public Integer refreshInterval = DEFAULT_REFRESH;

}
