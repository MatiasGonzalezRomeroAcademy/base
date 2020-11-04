package com.academy.base.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.academy.base.exception.InvalidCalculatorArgument;
import com.academy.base.properties.CalculatorProperties;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {
	@Mock
	private CalculatorProperties _calculatorProperties;

	@InjectMocks
	private CalculatorService calculatorService;

	@Test
	void when_add_it_shoul_add() {
		// prepare
		final BigDecimal firstNumber = BigDecimal.valueOf(1);
		final BigDecimal secondNumber = BigDecimal.valueOf(1);
		final BigDecimal expectedResult = BigDecimal.valueOf(2);

		// action
		final BigDecimal result = calculatorService.add(firstNumber, secondNumber);

		// assert
		assertNotNull(result);
		assertEquals(expectedResult, result);
	}

	@Test
	void when_devide_it_should_devide() {
		// prepare
		final BigDecimal firstNumber = BigDecimal.valueOf(10);
		final BigDecimal secondNumber = BigDecimal.valueOf(2);
		final BigDecimal expectedResult = BigDecimal.valueOf(5).setScale(10);
		when(_calculatorProperties.getDecimalPrecision()).thenReturn(10);

		// action
		final BigDecimal result = calculatorService.divide(firstNumber, secondNumber);

		// assert
		assertNotNull(result);
		assertEquals(expectedResult, result);
	}

	@Test
	void when_devide_by_zero_it_should_not_devide() {
		// prepare
		final BigDecimal firstNumber = BigDecimal.valueOf(10);
		final BigDecimal secondNumber = BigDecimal.valueOf(0);

		// action and assert
		assertThrows(InvalidCalculatorArgument.class, () -> {
			calculatorService.divide(firstNumber, secondNumber);
		});
	}

	@Test
	void when_substract_it_should_substract() {
		// prepare
		final BigDecimal firstNumber = BigDecimal.valueOf(10);
		final BigDecimal secondNumber = BigDecimal.valueOf(0);
		final BigDecimal expectedResult = BigDecimal.valueOf(10);

		// action
		final BigDecimal result = calculatorService.substract(firstNumber, secondNumber);

		// assert
		assertNotNull(result);
		assertEquals(expectedResult, result);
	}

	@Test
	void when_multiply_it_should_multiply() {
		// prepare
		final BigDecimal firstNumber = BigDecimal.valueOf(10);
		final BigDecimal secondNumber = BigDecimal.valueOf(0);
		final BigDecimal expectedResult = BigDecimal.valueOf(0);

		// action
		final BigDecimal result = calculatorService.multiply(firstNumber, secondNumber);

		// assert
		assertNotNull(result);
		assertEquals(expectedResult, result);
	}
}
