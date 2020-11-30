package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class TaskRepoTest {

    @Inject
    TaskRepo repo;

    @Test
    void findByName() {
        var task = repo.save(new Task("Test"));
        var name = repo.find("Test")
                .map(Task::getName)
                .orElse(null);
        assertEquals(task.getName(), name);
    }
}