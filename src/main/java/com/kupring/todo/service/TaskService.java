package com.kupring.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.kupring.todo.domain.Task;
import com.kupring.todo.dto.TaskDTO;
import com.kupring.todo.exception.TaskDuplicateIdException;
import com.kupring.todo.exception.TaskNotFoundException;
import com.kupring.todo.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	private static String TASK_NOT_FOUND = "Task with id %s not found";
	private static String TASK_CONFLICT = "Task with id %s already exist";

	public List<TaskDTO> getTasks() {
		List<Task> task = taskRepository.findAll();
		List<TaskDTO> taskDTOs = task.stream().map(t -> convertToDTO(t)).collect(Collectors.toList());
		
		return taskDTOs;
	}

	public TaskDTO getTask(Long id) throws TaskNotFoundException {
		Task task = taskRepository.findOne(id);
		if (null == task) {
			throw new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
		}
		
		return convertToDTO(task);
	}

	public TaskDTO createTask(TaskDTO taskDTO) throws TaskDuplicateIdException {
		Long taskId = taskDTO.getId();
		if (null != taskId && taskRepository.exists(taskId)) {
			throw new TaskDuplicateIdException(String.format(TASK_CONFLICT, taskId));
		}
		Task task = taskRepository.save(convertToEntity(taskDTO));
		
		return convertToDTO(task);
	}

	public void updateTask(Long id, TaskDTO taskDTO) throws TaskNotFoundException {
		Task currentTask = taskRepository.findOne(id);
		if (null == currentTask) {
			throw new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
		}
		taskRepository.save(convertToEntity(taskDTO));
	}

	public void updateTaskStatus(Long id, TaskDTO taskDTO) throws TaskNotFoundException {
		Task currentTask = taskRepository.findOne(id);
		if (null == currentTask) {
			throw new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
		}
		currentTask.setStatus(taskDTO.getStatus());
		taskRepository.save(currentTask);
	}

	public Long deleteTask(Long id) throws TaskNotFoundException {
		Task task = taskRepository.findOne(id);
		if (null == task) {
			throw new TaskNotFoundException(String.format(TASK_NOT_FOUND, id));
		}
		taskRepository.delete(id);
		
		return id;
	}

	private TaskDTO convertToDTO(Task task) {
		TaskDTO taskDTO = new ModelMapper().map(task, TaskDTO.class);
		
		return taskDTO;
	}

	private Task convertToEntity(TaskDTO taskDTO) {
		Task task = new ModelMapper().map(taskDTO, Task.class);
		task.setStatus(StringUtils.isEmpty(taskDTO.getStatus()) ? "pending" : taskDTO.getStatus());
		
		return task;
	}
}
