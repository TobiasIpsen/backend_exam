package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final TripRoutes tripRoutes = new TripRoutes();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/trips", tripRoutes.getRoutes());
        };
    }

}
