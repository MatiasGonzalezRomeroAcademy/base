package com.academy.base.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.academy.base.dto.CalculationResultDTO;
import com.academy.base.service.CalculatorService;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

	private CalculatorService calculatorService;

	@Autowired
	public CalculatorController(final CalculatorService calculatorService) {
		this.calculatorService = calculatorService;
	}

	@GetMapping("/has_battery")
	public boolean isAlive() {
		return true;
	}

	@GetMapping("/add/{firstNumber}/{secondNumber}")
	public CalculationResultDTO add( //
			@PathVariable double firstNumber, //
			@PathVariable double secondNumber //
	) {

		final BigDecimal result = calculatorService.add(BigDecimal.valueOf(firstNumber),
				BigDecimal.valueOf(secondNumber));

		final CalculationResultDTO response = new CalculationResultDTO();
		response.setResult(result);

		return response;
	}

	@GetMapping("/substract/{firstNumber}/{secondNumber}")
	public CalculationResultDTO substract( //
			@PathVariable double firstNumber, //
			@PathVariable double secondNumber //
	) {
		final BigDecimal result = calculatorService.substract( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)//
		);

		return calculatorResultToDTO(result);
	}

	@GetMapping("/multiply/{firstNumber}/{secondNumber}")
	public CalculationResultDTO multiply( //
			@PathVariable double firstNumber, //
			@PathVariable double secondNumber //
	) {
		final BigDecimal result = calculatorService.multiply( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)//
		);

		return calculatorResultToDTO(result);
	}

	@GetMapping("/divide/{firstNumber}/{secondNumber}")
	public CalculationResultDTO devide( //
			@PathVariable double firstNumber, //
			@PathVariable double secondNumber //
	) {
		final BigDecimal result = calculatorService.divide( //
				BigDecimal.valueOf(firstNumber), //
				BigDecimal.valueOf(secondNumber)//
		);
		return calculatorResultToDTO(result);
	}

	private CalculationResultDTO calculatorResultToDTO(final BigDecimal result) {
		final CalculationResultDTO calculatioResult = new CalculationResultDTO();
		calculatioResult.setResult(result);

		return calculatioResult;
	}
}
