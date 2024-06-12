package com.example.integracao.exception;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Class to process any type of error that we could have in the application
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
		log.error("Unexpected exception:{}, message:{} ", ex, ex.getMessage());
		return handleExceptionInternal(ex, obterRespostaDoErro(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ErrorDTO obterRespostaDoErro(Throwable ex) {
		return ErrorDTO.builder()
				.moreInfo(ex.getMessage()).dateTime(Date.from(ZonedDateTime.now().toInstant())).build();
	}

}
