package com.scale360.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
	private Long id;
	private String subject;
	private String detail;
	private String status;
}
