package com.academy.base.controller;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import com.academy.base.dto.ProjectDTO;
import com.academy.base.exception.ResourceNotFoundException;

class ProjectControllerTest extends AbstractControllerTest {
	final String basePath = "/api/projects";
	final String basePathWithId = "/api/projects/{id}";

	@Test
	@WithMockUser
	void shouldFindAll() throws Exception {
		// prepare
		final ProjectDTO projectOne = buildExampleProjectDTO();
		when(projectService.findAll()).thenReturn(singletonList(projectOne));

		// action
		final ResultActions result = mockMvc.perform(get(basePath));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$[0].id", is(1))) //
				.andExpect(jsonPath("$[0].name", is(projectOne.getName())));
	}

	@Test
	@WithMockUser
	void shouldFind() throws Exception {
		// prepare
		final ProjectDTO projectOne = buildExampleProjectDTO();
		when(projectService.findById(1L)).thenReturn(projectOne);

		// action
		final ResultActions result = mockMvc.perform(get(basePathWithId, "1"));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.id", is(1))) //
				.andExpect(jsonPath("$.name", is(projectOne.getName())));
	}

	@Test
	@WithMockUser
	void shouldSave() throws Exception {
		// prepare
		final ProjectDTO projectOne = buildExampleProjectDTO();
		final String payload = super.mapToJson(projectOne);
		when(projectService.save(projectOne)).thenReturn(projectOne);

		// action
		final ResultActions result = mockMvc.perform(post(basePath) //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(payload));

		// assert
		result.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	void shouldDelete() throws Exception {
		// prepare
		final ProjectDTO projectOne = buildExampleProjectDTO();
		doNothing().when(projectService).delete(projectOne.getId());

		// action
		final ResultActions result = mockMvc.perform(delete(basePathWithId, "1"));

		// result
		result.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser
	void shouldUpdate() throws Exception {
		// prepare
		final ProjectDTO projectOne = buildExampleProjectDTO();
		final String payload = super.mapToJson(projectOne);
		when(projectService.save(projectOne)).thenReturn(projectOne);

		// action
		final ResultActions result = mockMvc.perform(put(basePathWithId, "1") //
				.contentType(MediaType.APPLICATION_JSON) //
				.content(payload));

		// asserts
		result.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	void shouldGetErrorWhenSearchInvalidProject() throws Exception {
		// prepare
		final Long searchId = 1L;
		when(projectService.findById(searchId)).thenThrow(new ResourceNotFoundException("Project", "id", searchId));

		// action
		final ResultActions result = mockMvc.perform(get(basePathWithId, searchId));

		// asserts
		final String message = String.format("%s not found with %s : '%s'", "Project", "id", searchId);
		result.andExpect(status().isNotFound()) //
				.andExpect(jsonPath("$.message", is(message)));
	}

	private ProjectDTO buildExampleProjectDTO() {
		final ProjectDTO projectOne = new ProjectDTO();

		projectOne.setId(1L);
		projectOne.setName("Project 1");

		return projectOne;
	}
}
