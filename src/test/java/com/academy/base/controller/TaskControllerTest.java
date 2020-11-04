package com.academy.base.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import com.academy.base.dto.TaskDTO;
import com.academy.base.exception.ResourceNotFoundException;

class TaskControllerTest extends AbstractControllerTest {
	final String basePath = "/api/tasks";
	final String basePathWithId = "/api/tasks/{id}";

	@Test
	@WithMockUser(roles = "USER")
	void when_find_all_tasks_it_should_return_tasks() throws Exception {
		final TaskDTO taskResult = buildExampleTaskDTO();
		final Page<TaskDTO> pageResult = new PageImpl<>(Collections.singletonList(taskResult));
		when(taskService.findAll(any(String.class), any(Pageable.class))).thenReturn(pageResult);

		final ResultActions result = mockMvc.perform(get(basePath));

		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.content.[0].id", is(1))) //
				.andExpect(jsonPath("$.content.[0].description", is(taskResult.getDescription())));
	}

	@Test
	@WithMockUser(roles = "USER")
	void when_search_tasks_by_description_it_should_return_tasks() throws Exception {
		final TaskDTO taskResult = buildExampleTaskDTO();
		final String searchTerm = "Description example";
		taskResult.setDescription(searchTerm);
		final Page<TaskDTO> pageResult = new PageImpl<>(Collections.singletonList(taskResult));
		when(taskService.findAll(eq(searchTerm), any(Pageable.class))).thenReturn(pageResult);

		final ResultActions result = mockMvc.perform(get(basePath).param("description", searchTerm));

		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.content.[0].id", is(1))) //
				.andExpect(jsonPath("$.content.[0].description", is(searchTerm)));
	}

	@Test
	@WithMockUser(roles = "USER")
	void when_find_specific_task_it_should_return_task() throws Exception {
		final TaskDTO taskResult = buildExampleTaskDTO();
		when(taskService.findById(any(Long.class))).thenReturn(taskResult);

		final ResultActions result = mockMvc.perform(get(basePathWithId, "1"));

		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.id", is(1))) //
				.andExpect(jsonPath("$.description", is(taskResult.getDescription())));
	}

	@Test
	@WithMockUser(roles = "USER")
	void when_find_all_tasks_or_id_desc_it_should_pass_order_to_service() throws Exception {
		final ArgumentCaptor<String> searchTermCaptor = ArgumentCaptor.forClass(String.class);
		final ArgumentCaptor<Pageable> paginationCaptor = ArgumentCaptor.forClass(Pageable.class);

		final ResultActions result = mockMvc.perform(get(basePath) //
				.param("sort", "id,desc") //
				.param("sort", "name,asc"));

		verify(taskService).findAll(searchTermCaptor.capture(), paginationCaptor.capture());
		final Pageable pageable = paginationCaptor.getValue();
		final Sort sort = pageable.getSort();

		result.andExpect(status().isOk());
		assertEquals(Order.desc("id"), sort.getOrderFor("id"));
		assertEquals(Order.asc("name"), sort.getOrderFor("name"));
	}

	@Test
	@WithMockUser(roles = "USER")
	void when_find_specific_non_present_task_it_should_return_error_message() throws Exception {
		final TaskDTO taskResult = buildExampleTaskDTO();
		final Long searchId = taskResult.getId();
		when(taskService.findById(any(Long.class))).thenThrow(new ResourceNotFoundException("Task", "id", searchId));

		final ResultActions result = mockMvc.perform(get(basePathWithId, "1"));

		final String message = String.format("%s not found with %s : '%s'", "Task", "id", searchId);
		result.andExpect(status().isNotFound()) //
				.andExpect(jsonPath("$.message", is(message)));
	}

	@Test
	@WithMockUser
	void when_create_task_it_should_return_created_task() throws Exception {
		final TaskDTO taskResult = buildExampleTaskDTO();
		final String payload = super.mapToJson(taskResult);
		when(taskService.save(any(TaskDTO.class))).thenReturn(taskResult);

		final ResultActions result = mockMvc.perform(post(basePath) //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(payload));

		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	@WithMockUser
	void when_update_task_it_should_return_updated_task() throws Exception {
		final TaskDTO taskToUpdate = buildExampleTaskDTO();
		final String payload = super.mapToJson(taskToUpdate);
		when(taskService.update(any(Long.class), any(TaskDTO.class))).thenReturn(taskToUpdate);

		final ResultActions result = mockMvc.perform(put(basePathWithId, "1") //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(payload));

		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.id", is(1))) //
				.andExpect(jsonPath("$.description", is(taskToUpdate.getDescription())));
	}

	@Test
	@WithMockUser
	void when_update_non_present_task_it_should_return_error_message() throws Exception {
		final TaskDTO taskToUpdate = buildExampleTaskDTO();
		final Long searchId = taskToUpdate.getId();
		final String payload = super.mapToJson(taskToUpdate);
		when(taskService.update(any(Long.class), any(TaskDTO.class)))
				.thenThrow(new ResourceNotFoundException("Task", "id", searchId));

		final ResultActions result = mockMvc.perform(put(basePathWithId, "1") //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(payload));

		final String message = String.format("%s not found with %s : '%s'", "Task", "id", searchId);
		result.andExpect(status().isNotFound()) //
				.andExpect(jsonPath("$.message", is(message)));
	}

	@Test
	@WithMockUser
	void when_remove_task_it_should_return_success() throws Exception {
		doNothing().when(taskService).delete(any(Long.class));

		final ResultActions result = mockMvc.perform(delete(basePath + "/1"));

		result.andExpect(status().isNoContent());
		verify(taskService, times(1)).delete(any(Long.class));
	}

	@Test
	@WithMockUser
	void when_remove_non_present_task_it_should_return_error_message() throws Exception {
		doThrow(new ResourceNotFoundException("Task", "id", 1)).when(taskService).delete(any(Long.class));

		final ResultActions result = mockMvc.perform(delete(basePathWithId, "1"));

		final String message = String.format("%s not found with %s : '%s'", "Task", "id", 1);

		result.andExpect(status().isNotFound()) //
				.andExpect(jsonPath("$.message", is(message)));
		verify(taskService, times(1)).delete(any(Long.class));
	}

	private TaskDTO buildExampleTaskDTO() {
		final TaskDTO taskDTO = new TaskDTO();

		taskDTO.setId(1L);
		taskDTO.setDescription("A description");

		return taskDTO;
	}
}
