package com.example;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(rebuildContext = true)
@Property(name = "micronaut.security.enabled", value = "false")
public class ManagementTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    EmbeddedServer server;

    @Test
    void testEndpoints() {
        test(HttpRequest.GET("/beans"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/env"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/env/application"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/flyway"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/health"), HttpStatus.OK);
        test(HttpRequest.GET("/health/READINESS"), HttpStatus.OK);
        test(HttpRequest.GET("/info"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/loggers"), HttpStatus.NOT_FOUND);
        test(HttpRequest.POST("/refresh", ""), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.GET("/routes"), HttpStatus.UNAUTHORIZED);
        test(HttpRequest.POST("/stop", ""), HttpStatus.NOT_FOUND);
        test(HttpRequest.GET("/threaddump"), HttpStatus.UNAUTHORIZED);
    }

    @Test
    @Property(name = "endpoints.all.enabled", value = "true")
    @Property(name = "endpoints.all.sensitive", value = "false")
    @Property(name = "endpoints.loggers.write-sensitive", value = "false")
    void testEndpointsUnsecured() {
        server.refresh();
        test(HttpRequest.GET("/beans"), HttpStatus.OK);
        test(HttpRequest.GET("/env"), HttpStatus.OK);
        test(HttpRequest.GET("/env/application"), HttpStatus.OK);
        test(HttpRequest.GET("/flyway"), HttpStatus.OK);
        test(HttpRequest.GET("/health"), HttpStatus.OK);
        test(HttpRequest.GET("/health/READINESS"), HttpStatus.OK);
        test(HttpRequest.GET("/info"), HttpStatus.OK);
        test(HttpRequest.GET("/loggers"), HttpStatus.OK);
        test(HttpRequest.GET("/loggers/io.micronaut.configuration"), HttpStatus.OK);
        test(HttpRequest.POST(
                "/loggers/io.micronaut.configuration",
                "{\"configuredLevel\": \"DEBUG\"}"),
                HttpStatus.OK);
        test(HttpRequest.POST("/refresh", ""), HttpStatus.OK);
        test(HttpRequest.GET("/routes"), HttpStatus.OK);
        test(HttpRequest.POST("/stop", ""), HttpStatus.OK);
        test(HttpRequest.GET("/threaddump"), HttpStatus.OK);
    }

    void test(HttpRequest<?> request, HttpStatus status) {
        System.out.println(request.getMethod() + " " + request.getPath());
        HttpStatus actual;
        try {
            actual = client.toBlocking().exchange(request).getStatus();
        } catch (HttpClientResponseException e) {
            actual = e.getStatus();
        }
        assertEquals(status, actual);
    }
}
