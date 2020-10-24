package com.example;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
@Getter
@Setter
public class TaskDTO {

    @NotBlank
    private String name;

    @Mapper(componentModel = "jsr330")
    public interface TaskDTOMapper {

        List<TaskDTO> toDTOs(List<Task> tasks);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "done", ignore = true)
        Task toEntity(TaskDTO taskDTO);
    }
}
