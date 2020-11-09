package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Controller("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final TaskDTO.TaskDTOMapper mapper;

    @Get
    @Secured(SecurityRule.IS_ANONYMOUS)
    List<TaskDTO> list() {
        return mapper.map(service.getTasks());
    }

    @Post
    @Secured(SecurityRule.IS_AUTHENTICATED)
    Task add(@Valid TaskDTO task) {
        return service.save(mapper.map(task));
    }

    @Error
    HttpResponse<String> errorHandler(DuplicateEntryException e) {
        return HttpResponse.badRequest().body(e.getMessage());
    }
}
