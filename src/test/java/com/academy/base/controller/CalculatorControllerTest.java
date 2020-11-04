package com.academy.base.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import com.academy.base.exception.InvalidCalculatorArgument;

class CalculatorControllerTest extends AbstractControllerTest {
	final String basePath = "/api/calculator/{param}/{firstNumber}/{secondNumber}";

	@Test
	@WithMockUser
	void shouldHasBattery() throws Exception {
		// action
		final ResultActions result = mockMvc.perform(get("/api/calculator/has_battery"));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$", is(true)));
	}

	@Test
	@WithMockUser
	void shouldAdd() throws Exception {
		// prepare
		final double firstNumber = 1;
		final double secondNumber = 1;
		final double expectedResult = 2;
		when(calculatorService.add( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)) //
		).thenReturn(BigDecimal.valueOf(expectedResult));

		// action
		final ResultActions result = mockMvc.perform( //
				get(basePath, "add", firstNumber, secondNumber));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.result", is(expectedResult)));
	}

	@Test
	@WithMockUser
	void shouldSubstract() throws Exception {
		// prepare
		final double firstNumber = 3;
		final double secondNumber = 1;
		final double expectedResult = 2;
		when(calculatorService.substract( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)) //
		).thenReturn(BigDecimal.valueOf(expectedResult));

		// action
		final ResultActions result = mockMvc.perform( //
				get(basePath, "substract", firstNumber, secondNumber));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.result", is(expectedResult)));
	}

	@Test
	@WithMockUser
	void shouldMultiply() throws Exception {
		// prepare
		final double firstNumber = 3;
		final double secondNumber = 1;
		final double expectedResult = 3;
		when(calculatorService.multiply( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)) //
		).thenReturn(BigDecimal.valueOf(expectedResult));

		// action
		final ResultActions result = mockMvc.perform( //
				get(basePath, "multiply", firstNumber, secondNumber));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.result", is(expectedResult)));
	}

	@Test
	@WithMockUser
	void shouldDivide() throws Exception {
		// prepare
		final double firstNumber = 3;
		final double secondNumber = 1;
		final double expectedResult = 3;
		when(calculatorService.divide( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)) //
		).thenReturn(BigDecimal.valueOf(expectedResult));

		// action
		final ResultActions result = mockMvc.perform( //
				get(basePath, "divide", firstNumber, secondNumber));

		// assert
		result.andExpect(status().isOk()) //
				.andExpect(jsonPath("$.result", is(expectedResult)));
	}

	@Test
	@WithMockUser
	void shouldNotDevideByZero() throws Exception {
		// prepare
		final double firstNumber = 3;
		final double secondNumber = 0;
		final String errorMessage = "Could not devide by zero";
		when(calculatorService.divide( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)) //
		).thenThrow(new InvalidCalculatorArgument(errorMessage));

		// action
		final ResultActions result = mockMvc.perform( //
				get(basePath, "divide", firstNumber, secondNumber));

		// assert
		result.andExpect(status().isBadRequest()) //
				.andExpect(jsonPath("$.message", is(errorMessage)));
	}
}
