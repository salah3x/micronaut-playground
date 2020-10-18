package com.example;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Introspected
@Getter
@Setter
public class TaskDTO {

    @NotBlank
    private String name;
}
