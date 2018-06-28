package com.gooooogolf.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gooooogolf.todo.domain.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
