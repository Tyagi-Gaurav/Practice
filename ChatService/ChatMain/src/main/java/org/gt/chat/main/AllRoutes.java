package org.gt.chat.main;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import org.gt.chat.main.resource.HealthCheckResource;
import org.gt.chat.main.resource.MessageResourceAkka;

public class AllRoutes extends AllDirectives {
    private final MessageResourceAkka messageResource;
    private final HealthCheckResource healthCheckResource;

    public AllRoutes(MessageResourceAkka messageResource, HealthCheckResource healthCheckResource) {
        this.messageResource = messageResource;
        this.healthCheckResource = healthCheckResource;
    }


    public Route getRoutes() {
        return route(messageResource.getRoute(),
                healthCheckResource.getRoute());
    }
}
