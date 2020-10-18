package com.example;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.transaction.annotation.ReadOnly;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends CrudRepository<Task, UUID> {

    @ReadOnly
    Optional<Task> findByName(@NotNull String name);
}