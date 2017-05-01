package com.scale360.todo.controller.advice;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scale360.todo.exception.TaskDuplicateIdException;
import com.scale360.todo.exception.TaskNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {

	@ExceptionHandler(TaskDuplicateIdException.class)
	public void handleTaskDuplicate(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.CONFLICT.value());
	}
	
	@ExceptionHandler(TaskNotFoundException.class)
	public void handleTaskNotFound(HttpServletResponse response) throws Exception {
		response.sendError(HttpStatus.NOT_FOUND.value());
	}

}
