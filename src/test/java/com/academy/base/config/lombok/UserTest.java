package com.academy.base.config.lombok;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

	@Test
	void testLombokUser() {
		// prepare
		final String name = "Matias";
		final int age = 25;

		// action
		final User user = User.builder() //
				.name(name) //
				.age(age) //
				.build();

		// assert
		assertEquals(name, user.getName());
		assertEquals(age, user.getAge());
	}
}
