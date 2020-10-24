package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Controller("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final TaskDTO.TaskDTOMapper mapper;

    @Get
    List<TaskDTO> list() {
        return mapper.toDTOs(service.getTasks());
    }

    @Post
    Task add(@Valid TaskDTO task) {
        return service.save(mapper.toEntity(task));
    }

    @Error
    HttpResponse<String> errorHandler(DuplicateEntryException e) {
        return HttpResponse.badRequest().body(e.getMessage());
    }
}
