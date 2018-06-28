package com.gooooogolf.todo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Task")
@Getter
@Setter
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "subject")
	private String subject;
	
	@Column(name = "detail", nullable = true)
	private String detail;
	
	@Column(name = "status")
	private String status;
}
