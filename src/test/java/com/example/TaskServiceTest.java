package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class TaskServiceTest {

    @Inject
    TaskService service;

    @Test
    void getTasks() {
        assertEquals(4, service.getTasks().size());
    }

    @Test
    void save() {
        var task = service.save(new Task("Test"));
        assertNotNull(task.getId());
        assertEquals(false, task.getDone());

        assertThrows(
                ConstraintViolationException.class,
                () -> service.save(null));
    }
}