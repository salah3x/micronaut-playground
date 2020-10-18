package com.example;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.transaction.annotation.ReadOnly;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepo repo;

    @ReadOnly
    public Iterable<Task> getTasks() {
        return repo.findAll();
    }

    @Transactional
    public Task save(@NotNull Task task) {
        return repo.save(task);
    }

    // todo move to migration file
    @EventListener
    void bootstrap(StartupEvent event) {
        if (repo.count() == 0) {
            log.info("Populating database");
            repo.saveAll(Arrays.asList(
                    new Task("Task 1"),
                    new Task("Task 2"),
                    new Task("Task 3"),
                    new Task("Task 4")
            ));
        }
    }
}