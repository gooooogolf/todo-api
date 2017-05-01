package com.scale360.todo.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.scale360.todo.ApplicationTests;
import com.scale360.todo.dto.TaskDTO;
import com.scale360.todo.exception.TaskNotFoundException;
import com.scale360.todo.service.TaskService;

@RunWith(SpringRunner.class)
public class TaskControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TaskService taskService;

	@InjectMocks
	TaskController taskController;

	private String TASK_NOT_FOUND = "Task with id %s not found";

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(taskController)
				.setHandlerExceptionResolvers(ApplicationTests.withExceptionControllerAdvice()).build();
	}

	private TaskDTO taskBuilder(TaskDTO taskDTO) {
		taskDTO.setId(1L);
		taskDTO.setSubject("test_subject");
		taskDTO.setDetail("test_detail");
		taskDTO.setStatus("pending");

		return taskDTO;
	}

	@Test
	public void shouldReturnTasksWhenGetTaskList() throws Exception {
		TaskDTO taskDTO = taskBuilder(new TaskDTO());

		List<TaskDTO> taskDTOs = Arrays.asList(taskDTO);
		Mockito.when(taskService.getTasks()).thenReturn(taskDTOs);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/tasks")
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].detail", is("test_detail")));
	}

	@Test
	public void shouldReturnTaskWhenGetTaskById() throws Exception {
		TaskDTO taskDTO = taskBuilder(new TaskDTO());

		Mockito.when(taskService.getTask(1L)).thenReturn(taskDTO);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/tasks/" + 1)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.detail", is("test_detail")));

	}

	@Test
	public void shouldReturnHttpStatusNotFoundWhenGetTaskWithNotTaskId() throws Exception {
		Mockito.when(taskService.getTask(1L)).thenThrow(new TaskNotFoundException(String.format(TASK_NOT_FOUND, 1)));

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/tasks/" + 1)
				.contentType(MediaType.APPLICATION_JSON);
		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void shouldReturnTaskWhenCreateTask() throws Exception {
		TaskDTO expectedTask = taskBuilder(new TaskDTO());
		Mockito.when(taskService.createTask(Matchers.anyObject())).thenReturn(expectedTask);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/v1/tasks")
				.content("{\"subject\":\"test_subject\",\"detail\":\"test_detail\"}")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.detail", is("test_detail")));
	}

	@Test
	public void shouldReturnHttpStatusOKWhenUpdateTask() throws Exception {
		Mockito.doNothing().when(taskService).updateTask(Matchers.anyLong(), Matchers.anyObject());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/v1/tasks/1")
				.content("{\"id\":1,\"subject\":\"test_subject\",\"detail\":\"test_detail\"}")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldReturnHttpStatusOKWhenUpdateTaskStatus() throws Exception {
		Mockito.doNothing().when(taskService).updateTaskStatus(Matchers.anyLong(), Matchers.anyObject());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.patch("/api/v1/tasks/1")
				.content("{\"status\":\"done\"}").contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void shouldReturnTaskIdWhenDeleteTaskById() throws Exception {
		Mockito.when(taskService.deleteTask(Matchers.anyLong())).thenReturn(1L);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/v1/tasks/1").contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$", is(1)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
