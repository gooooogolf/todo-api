package com.kupring.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kupring.todo.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
