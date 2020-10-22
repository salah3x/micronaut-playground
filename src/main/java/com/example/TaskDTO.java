package com.example;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Introspected
@Getter
@AllArgsConstructor
public class TaskDTO {

    @NotBlank
    private final String name;
}
