package com.example.identityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;


@Getter
public enum ErrorCode {
	INVALID_KEY(1001, "Invalid message key!", HttpStatus.BAD_REQUEST),
	UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception",HttpStatus.INTERNAL_SERVER_ERROR),
	USER_EXIST(1002,"User exists", HttpStatus.BAD_REQUEST),
	USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
	PASSWORD_INVALID(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
	USER_NOT_EXIST(1005, "User not exist", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.BAD_REQUEST);
	
	private int code;
	private String message;
	private HttpStatusCode statusCode;
	
	private ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	
}
