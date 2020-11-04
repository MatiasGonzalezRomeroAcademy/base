package com.academy.base.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

class ExceptionAdviceTest extends AbstractControllerTest {

	@Test
	@WithMockUser
	void when_exception_thrown_should_return_message() throws Exception {
		// prepare
		when(projectService.findAll()).thenThrow(new RuntimeException());

		// action
		final ResultActions result = mockMvc.perform(get("/api/projects"));

		// assert
		result.andExpect(status().isInternalServerError()) //
				.andExpect(jsonPath("$.message",
						is("Internal server error, somthing goes wrong, check the logs for more details")));
	}
}
