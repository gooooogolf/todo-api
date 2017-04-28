package com.scale360.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scale360.todo.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
