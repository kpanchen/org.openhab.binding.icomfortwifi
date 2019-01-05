package org.openhab.binding.icomfortwifi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.eclipse.smarthome.io.net.http.HttpClientFactory;
import org.openhab.binding.icomfortwifi.handler.iComfortWiFiBridgeHandler;
import org.openhab.binding.icomfortwifi.handler.iComfortWiFiHeatingZoneHandler;
import org.openhab.binding.icomfortwifi.handler.iComfortWiFiTemperatureControlSystemHandler;
import org.openhab.binding.icomfortwifi.internal.discovery.iComfortWiFiDiscoveryService;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the thing factory for this binding
 *
 * @author Konstantin Panchenko - Initial contribution
 *
 */

@Component(service = ThingHandlerFactory.class, configurationPid = "binding.icomfortwifi")
public class iComfortWiFiHandlerFactory extends BaseThingHandlerFactory {

    private final Map<ThingUID, @Nullable ServiceRegistration<?>> discoveryServiceRegs = new HashMap<>();
    private HttpClient httpClient;

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return iComfortWiFiBindingConstants.SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();
        if (thingTypeUID.equals(iComfortWiFiBindingConstants.THING_TYPE_ICOMFORT_ACCOUNT)) {
            iComfortWiFiBridgeHandler bridge = new iComfortWiFiBridgeHandler((Bridge) thing, httpClient);
            registeriComfortWiFiDiscoveryService(bridge);
            return bridge;
        } else if (thingTypeUID.equals(iComfortWiFiBindingConstants.THING_TYPE_ICOMFORT_THERMOSTAT)) {
            return new iComfortWiFiTemperatureControlSystemHandler(thing);
        } else if (thingTypeUID.equals(iComfortWiFiBindingConstants.THING_TYPE_ICOMFORT_ZONE)) {
            return new iComfortWiFiHeatingZoneHandler(thing);
        }

        return null;
    }

    private void registeriComfortWiFiDiscoveryService(iComfortWiFiBridgeHandler iComfortWiFiBridgeHandler) {
        iComfortWiFiDiscoveryService discoveryService = new iComfortWiFiDiscoveryService(iComfortWiFiBridgeHandler);

        this.discoveryServiceRegs.put(iComfortWiFiBridgeHandler.getThing().getUID(), bundleContext
                .registerService(DiscoveryService.class.getName(), discoveryService, new Hashtable<String, Object>()));
    }

    @Override
    public ThingHandler registerHandler(Thing thing) {
        return super.registerHandler(thing);
    }

    @Override
    protected void removeHandler(ThingHandler thingHandler) {
        if (thingHandler instanceof iComfortWiFiBridgeHandler) {
            ServiceRegistration<?> serviceReg = this.discoveryServiceRegs.get(thingHandler.getThing().getUID());
            if (serviceReg != null) {
                iComfortWiFiDiscoveryService service = (iComfortWiFiDiscoveryService) bundleContext
                        .getService(serviceReg.getReference());
                if (service != null) {
                    service.deactivate();
                }
                serviceReg.unregister();
                discoveryServiceRegs.remove(thingHandler.getThing().getUID());
            }
        }
    }

    @Reference
    protected void setHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClient = httpClientFactory.getCommonHttpClient();
    }

    protected void unsetHttpClientFactory(HttpClientFactory httpClientFactory) {
        this.httpClient = null;
    }

}