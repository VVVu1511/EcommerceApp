package com.Vu.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringController {
	@GetMapping("/hello")
	String sayHello() {
		return "Hello time, again";
	}
	@GetMapping("/vu")
	String lover() {
		return "Nguyen Tuong Vy";
	}
}
