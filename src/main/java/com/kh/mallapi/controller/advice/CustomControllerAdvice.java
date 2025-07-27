package com.kh.mallapi.controller.advice;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kh.mallapi.util.CustomJWTException;

@RestControllerAdvice
public class CustomControllerAdvice {

	// 없는 데이터를 요청했을때
	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<?> notExist(NoSuchElementException e) {
		String msg = e.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
	}

	// 폼 양싯에 맞지 않는 데이터를 전송했을때
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {
		String msg = e.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
	}

	// 예외를 처리하고 싶을때 추가
	@ExceptionHandler(CustomJWTException.class)
	protected ResponseEntity<?> handleJWTException(CustomJWTException e) {
		String msg = e.getMessage();
		return ResponseEntity.ok().body(Map.of("error", msg));
	}

}
