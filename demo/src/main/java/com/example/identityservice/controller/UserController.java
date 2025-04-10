package com.example.identityservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
	UserService userService;
	
	@PostMapping
	ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
		ApiResponse<User> apiResponse = new ApiResponse<>();
		
		apiResponse.setResult(userService.createUser(request));
		
		return apiResponse;
	}
	
	@GetMapping
	List<User> getUsers(){
		return userService.getUser();
	}
	
	@GetMapping("/{userId}")
	UserResponse getUser(@PathVariable("userId") String userId) {
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}")
	UserResponse updateUser(@PathVariable String userId,@RequestBody @Valid UserUpdateRequest request) {
		return userService.updateUser(userId, request);
	}
	
	@DeleteMapping("/{userId}")
	
	String deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		
		return "User has been deleted";
	}
	
}
