package com.example.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	User createUser(@RequestBody UserCreationRequest request) {
		return userService.createUser(request);
	}
	
	@GetMapping
	List<User> getUsers(){
		return userService.getUser();
	}
}
