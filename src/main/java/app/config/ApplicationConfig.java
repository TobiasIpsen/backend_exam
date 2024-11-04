package app.config;

import app.exceptions.ApiException;
import app.routes.Routes;
import app.security.controllers.AccessController;
import app.utils.Utils;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfig {

    private static Routes routes = new Routes();
    private static AccessController accessController = new AccessController();
    private static Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    public static void configuration(JavalinConfig config) {
        config.showJavalinBanner = false;
        config.bundledPlugins.enableRouteOverview("/routes");
        config.router.contextPath = "/api"; // base path for all endpoints
        config.router.apiBuilder(routes.getRoutes());
    }

    public static Javalin startServer(int port) {
        Javalin app = Javalin.create(ApplicationConfig::configuration);

        app.beforeMatched(accessController::accessHandler);

        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);
        app.exception(EntityNotFoundException.class, ApplicationConfig::entityNotFound);
        app.exception(Exception.class, ApplicationConfig::generalExceptionHandler);

        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    //--------------Exceptions--------------//

    private static void generalExceptionHandler(Exception e, Context ctx) {
        logger.error("An unhandled exception occurred: Message: {}", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "error", e.getMessage()));
    }

    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }

    public static void entityNotFound(Exception e, Context ctx) {
        ctx.status(HttpStatus.NOT_FOUND);
        logger.warn("Entity Not Found: Message: {}", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }
}
