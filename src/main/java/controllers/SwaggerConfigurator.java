package controllers;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import javax.ws.rs.core.MediaType;

@SwaggerDefinition(
        basePath = "/Secured-API-Example/webapi",
        consumes = {MediaType.APPLICATION_JSON},
        produces = {MediaType.APPLICATION_JSON},
        schemes = {SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS},
        info = @Info(title = "REST Endpoints", description = "REST endpoint documentation", version = "V0.0.0")
)
public interface SwaggerConfigurator {
}
