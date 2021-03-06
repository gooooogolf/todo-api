package com.gooooogolf.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gooooogolf.todo.dto.TaskDTO;
import com.gooooogolf.todo.service.TaskService;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping(value = "/tasks")
	public ResponseEntity<?> getTasks() {		
		return ResponseEntity.ok(taskService.getTasks());
	}

	@PostMapping(value = "/tasks")
	public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {
		return ResponseEntity.status( HttpStatus.CREATED).body(taskService.createTask(taskDTO));
	}

	@GetMapping(value = "/tasks/{id}")
	public ResponseEntity<?> getTask(@PathVariable("id") Long id) {
		return ResponseEntity.ok(taskService.getTask(id));
	}

	@PutMapping(value = "/tasks/{id}")
	public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
		taskService.updateTask(id, taskDTO);

		return ResponseEntity.ok(null);
	}

	@PatchMapping(value = "/tasks/{id}")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") Long id, @RequestBody TaskDTO taskDTO) {
		taskService.updateTaskStatus(id, taskDTO);
		
		return ResponseEntity.ok(null);
	}

	@DeleteMapping(value = "/tasks/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
		return ResponseEntity.ok(taskService.deleteTask(id));
	}
}
