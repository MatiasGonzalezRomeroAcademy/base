package com.academy.base.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.academy.base.service.CalculatorService;
import com.academy.base.service.JpaUserDetailService;
import com.academy.base.service.ProjectService;
import com.academy.base.service.RoleService;
import com.academy.base.service.TaskService;
import com.academy.base.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public abstract class AbstractControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected ProjectService projectService;

	@MockBean
	protected CalculatorService calculatorService;

	@MockBean
	protected UserService UserService;

	@MockBean
	protected TaskService taskService;

	@MockBean
	protected JpaUserDetailService jpaUserDetailService;

	@MockBean
	protected RoleService RoleService;

	@MockBean
	protected AuthenticationManager authenticationManager;

	protected String mapToJson(final Object obj) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.writeValueAsString(obj);
	}
}