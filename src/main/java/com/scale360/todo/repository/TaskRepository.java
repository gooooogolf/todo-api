package com.scale360.todo.repository;

import org.springframework.data.repository.CrudRepository;

import com.scale360.todo.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
