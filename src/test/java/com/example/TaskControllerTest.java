package com.example;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
@Property(name = "micronaut.security.enabled", value = "false")
class TaskControllerTest {

    @Inject
    @Client("/tasks")
    HttpClient client;

    @Test
    void testController() {
        var tasks = client.toBlocking()
                .retrieve(HttpRequest.GET("/"), Argument.listOf(Task.class));
        Assertions.assertEquals(4, tasks.size());

        var exception = Assertions.assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.POST("/", new TaskDTO(null)))
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        exception = Assertions.assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.POST("/", new TaskDTO("")))
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        var task = client.toBlocking()
                .retrieve(HttpRequest.POST("/", new TaskDTO("Task")), Argument.of(Task.class));
        Assertions.assertNotNull(task.getId());
        Assertions.assertEquals("Task", task.getName());
        Assertions.assertEquals(false, task.getDone());

        exception = Assertions.assertThrows(
                HttpClientResponseException.class,
                () -> client.toBlocking().exchange(HttpRequest.POST("/", new TaskDTO("Task")))
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(
                "Task with name 'Task' already exists.",
                exception.getResponse().getBody(String.class).orElse(null)
        );

        var tasks2 = client.toBlocking()
                .retrieve(HttpRequest.GET("/"), Argument.listOf(Task.class));
        Assertions.assertEquals(5, tasks2.size());
    }
}