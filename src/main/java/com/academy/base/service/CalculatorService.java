package com.academy.base.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.academy.base.exception.InvalidCalculatorArgument;
import com.academy.base.properties.CalculatorProperties;

@Service
public class CalculatorService {

	private CalculatorProperties calculatorProperties;

	@Autowired
	public CalculatorService(final CalculatorProperties calculatorProperties) {
		this.calculatorProperties = calculatorProperties;
	}

	public BigDecimal divide(final BigDecimal firstNumber, final BigDecimal secondNumber) {
		if (secondNumber.compareTo(BigDecimal.ZERO) == 0) {
			throw new InvalidCalculatorArgument("Could not devide by zero");
		}

		return firstNumber.divide(secondNumber, calculatorProperties.getDecimalPrecision(), RoundingMode.HALF_UP);
	}

	public BigDecimal add(final BigDecimal firstNumber, final BigDecimal secondNumber) {
		return firstNumber.add(secondNumber);
	}

	public BigDecimal substract(final BigDecimal firstNumber, final BigDecimal secondNumber) {
		return firstNumber.subtract(secondNumber);
	}

	public BigDecimal multiply(final BigDecimal firstNumber, final BigDecimal secondNumber) {
		return firstNumber.multiply(secondNumber);
	}

}
