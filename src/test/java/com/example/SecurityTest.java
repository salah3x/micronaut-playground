package com.example;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
public class SecurityTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void test() {
        var response = client.toBlocking().exchange(HttpRequest.GET("/tasks"));
        Assertions.assertEquals(HttpStatus.OK, response.status());

        var exception = Assertions.assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.POST("/tasks", new TaskDTO("Some Task")))
        );
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());

        var credentials = new UsernamePasswordCredentials("salah", "123");
        var token = client.toBlocking()
                .retrieve(HttpRequest.POST("/login", credentials), BearerAccessRefreshToken.class);

        Assertions.assertEquals("salah", token.getUsername());
        Assertions.assertNotNull(token.getAccessToken());
        Assertions.assertNotNull(token.getRefreshToken());

        response = client.toBlocking().exchange(
                HttpRequest.POST("/tasks", new TaskDTO("Some Task")).bearerAuth(token.getAccessToken()));
        Assertions.assertEquals(HttpStatus.OK, response.status());
    }
}
