package com.algo.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.algo.exception.custom.SignUpFailException;
import com.algo.exception.dto.ExceptionResponse;
import com.sun.jdi.request.DuplicateRequestException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(value = {
		InternalAuthenticationServiceException.class,
		BadCredentialsException.class,
		NoSuchElementException.class,
		SignUpFailException.class,
		IllegalArgumentException.class,
		DuplicateRequestException.class
	})
	public ResponseEntity<ExceptionResponse> handleCustomException(RuntimeException ex) {
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(ExceptionResponse
				.builder()
				.message(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value())
				.build()
			);
	}
}
