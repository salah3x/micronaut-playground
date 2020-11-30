package com.example;

import io.micronaut.transaction.annotation.ReadOnly;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepo repo;

    @ReadOnly
    public List<Task> getTasks() {
        return repo.findAll();
    }

    @Transactional
    public Task save(@NotNull Task task) {
        repo.find(task.getName())
                .ifPresent(t -> {
                    var message = "Task with name '" + t.getName() + "' already exists.";
                    throw new DuplicateEntryException(message);
                });
        return repo.save(task);
    }
}