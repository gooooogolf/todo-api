package com.gooooogolf.todo.exception;

public class TaskDuplicateIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaskDuplicateIdException(String message) {
		super(message);
	}


}
