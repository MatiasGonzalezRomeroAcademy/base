package com.academy.base.controller;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.academy.base.dto.ErrorResponseDTO;
import com.academy.base.exception.InvalidCalculatorArgument;
import com.academy.base.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAdvice.class);

	private MessageSource messageSource;

	@Autowired
	public ExceptionAdvice(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponseDTO resourceNotFoundHandler(final ResourceNotFoundException ex) {
		LOGGER.error("Not found error: ", ex);

		return ErrorResponseDTO.builder() //
				.message(ex.getMessage()) //
				.build();
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseDTO exceptionHandler(final Exception ex) {
		LOGGER.error("Unexpected error: ", ex);

		return ErrorResponseDTO.builder()//
				.message("Internal server error, somthing goes wrong, check the logs for more details") //
				.build();
	}

	@ResponseBody
	@ExceptionHandler(InvalidCalculatorArgument.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponseDTO invalidCalculatorExceptionHandler(final InvalidCalculatorArgument ex) {
		LOGGER.debug("Invalid calculator argument error: ", ex);

		final String message = messageSource.getMessage("api.messages.error.calculator.could-not-devide-by-zero", null,
				LocaleContextHolder.getLocale());

		return ErrorResponseDTO.builder()//
				.message(message) //
				.build();
	}

	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponseDTO invalidArgumentExceptionHandler(final IllegalArgumentException ex) {
		LOGGER.debug("Invalid argument error: ", ex);

		return ErrorResponseDTO.builder()//
				.message(ex.getMessage()) //
				.build();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
		final Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return errors;
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ErrorResponseDTO handleValidationExceptions(final AccessDeniedException ex) {
		LOGGER.debug("Invalid access:", ex);

		return ErrorResponseDTO.builder()//
				.message(ex.getMessage()) //
				.build();
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ErrorResponseDTO handleMaxSizeException(MaxUploadSizeExceededException exc) {
		final String message = messageSource.getMessage("api.messages.error.max-size", null, getLocale());

		return ErrorResponseDTO.builder()//
				.message(message) //
				.build();
	}

}
