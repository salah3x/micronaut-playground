package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

@Controller("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @Get
    Iterable<Task> list() {
        return service.getTasks();
    }

    @Post
    Task add(@Valid TaskDTO task) {
        return service.save(new Task(task.getName()));
    }

    @Error
    HttpResponse<Void> errorHandler(PersistenceException e) {
        return HttpResponse.badRequest();
    }
}
