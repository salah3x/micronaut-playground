package com.example;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class OpenAPITest {

    @Inject
    @Client("/swagger")
    HttpClient client;

    @Test
    void testSwagger() {
        var response = client.toBlocking()
                .exchange(HttpRequest.GET("/demo-0.0.yml"), Argument.STRING);
        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().startsWith("openapi: 3.0.1"));

        response = client.toBlocking().exchange(HttpRequest.GET("/"), Argument.STRING);
        assertEquals(HttpStatus.OK, response.status());
        assertTrue(response.getBody().isPresent());
        assertTrue(response.getBody().get().contains("demo-0.0"));
    }
}
