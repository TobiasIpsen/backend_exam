package app.routes;

import app.controllers.TripController;
import app.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final TripController controller = new TripController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/", controller::getAll, Role.USER);
            get("/{id}", controller::getById);
            post("/", controller::create);
            put("/{id}", controller::update);
            delete("/{id}", controller::delete);
            put("/{tripId}/guides/{guideId}", controller::addGuide);
            post("/populate", controller::populate);
            get("/{guideId}", controller::tripsByGuide);
            get("/category/{category}", controller::tripsByCategory);
            get("/guides/totalPrice", controller::guidesTotalPrice);
            get("/category/packinglist/{category}", controller::tripsPackingList);
            get("/{id}/packingweight", controller::tripsPackingWeight);
        };
    }
}
