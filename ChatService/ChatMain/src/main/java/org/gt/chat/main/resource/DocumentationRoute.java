package org.gt.chat.main.resource;

import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.jaxrs.Reader;
import io.swagger.jaxrs.config.DefaultReaderConfig;
import io.swagger.models.Swagger;
import io.swagger.util.Json;

public class DocumentationRoute extends AllDirectives {
    private DefaultReaderConfig readerConfig;

    public DocumentationRoute(DefaultReaderConfig readerConfig) {
        this.readerConfig = readerConfig;
    }

    public Route routes() {
            return route(
                    swaggerApiDocsRoute(),
                    swaggerRoute(),
                    getFromResourceDirectory("swagger-ui")
            );
    }

    private Route swaggerRoute() {
        return path(PathMatchers.segment("swagger"),
                () -> getFromResource("swagger-ui/index.html"));
    }

    private Route swaggerApiDocsRoute() {
        return path(PathMatchers.segment("api-docs").slash(PathMatchers.segment("swagger.json")),
                () -> get(() -> complete(swaggerJson())));
    }

    private String swaggerJson() {
        try {
            final Swagger swaggerConfig = new Swagger();
            final Reader reader = new Reader(swaggerConfig, readerConfig);
            final Swagger swagger = reader.read(MessageResourceAkka.class);
            return Json.pretty().writeValueAsString(swagger);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}