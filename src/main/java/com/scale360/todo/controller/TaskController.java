package com.scale360.todo.controller;

import java.util.List;

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

import com.scale360.todo.domain.Task;
import com.scale360.todo.repository.TaskRepository;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	private static String TASK_NOT_FOUND = "Task with id %s not found";
	private static String TASK_CONFLICT = "Task with id %s already exist";
	 
	@GetMapping(value = "/tasks")
	public ResponseEntity<?> getTasks() {
		List<Task> tasks = taskRepository.findAll();		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}

	@PostMapping(value = "/tasks")
	public ResponseEntity<?> createTask(@RequestBody Task task) {
		Long taskId = task.getId();
		if (taskRepository.exists(taskId)) {
			return new ResponseEntity<>(String.format(TASK_CONFLICT, taskId), HttpStatus.CONFLICT);
		}

		taskRepository.save(task);
		return new ResponseEntity<>(task, HttpStatus.CREATED);
	}

	@GetMapping(value = "/tasks/{id}")
	public ResponseEntity<?> getTask(@PathVariable("id") Long id) {
		Task task = taskRepository.findOne(id);
		if (null == task) {
			return new ResponseEntity<>(String.format(TASK_NOT_FOUND, id), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}

	@PutMapping(value = "/tasks/{id}")
	public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
		Task currentTask = taskRepository.findOne(id);
		if (null == currentTask) {
			return new ResponseEntity<>(String.format(TASK_NOT_FOUND, id), HttpStatus.NOT_FOUND);
		}

		taskRepository.save(task);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PatchMapping(value = "/tasks/{id}")
	public ResponseEntity<?> updateTaskStatus(@PathVariable("id") Long id, @RequestBody Task task) {
		Task currentTask = taskRepository.findOne(id);
		if (null == currentTask) {
			return new ResponseEntity<>(String.format(TASK_NOT_FOUND, id), HttpStatus.NOT_FOUND);
		}
		
		currentTask.setStatus(task.getStatus());
		taskRepository.save(task);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/tasks/{id}")
	public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
		Task task = taskRepository.findOne(id);
		if (null == task) {
			return new ResponseEntity<>(String.format(TASK_NOT_FOUND, id), HttpStatus.NOT_FOUND);
		}

		taskRepository.delete(id);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
}
