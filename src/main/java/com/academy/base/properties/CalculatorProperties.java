package com.academy.base.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("calculator")
@Getter
@Setter
public class CalculatorProperties {

	private int decimalPrecision;
}
