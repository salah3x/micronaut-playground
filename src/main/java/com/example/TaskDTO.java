package com.example;

import io.micronaut.core.annotation.Introspected;
import lombok.Value;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
@Value
public class TaskDTO {

    @NotBlank
    String name;

    @Mapper(componentModel = "jsr330")
    public interface TaskDTOMapper {

        List<TaskDTO> map(List<Task> tasks);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "done", ignore = true)
        Task map(TaskDTO taskDTO);
    }
}
