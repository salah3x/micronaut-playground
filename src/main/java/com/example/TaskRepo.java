package com.example;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface TaskRepo extends CrudRepository<Task, Long> {

    @ReadOnly
    Optional<Task> findByName(@NotNull String name);
}