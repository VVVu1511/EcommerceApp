package com.example.identityservice.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.service.AuthenticationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@PostMapping("/log-in")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		AuthenticationResponse result = authenticationService.authenticate(request);
		
		return ApiResponse.<AuthenticationResponse>builder()
		        .result(result)
		        .build();
	}
	
	@PostMapping("/introspect")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody IntrospectRequest request){
		IntrospectRequest result = authenticationService.authenticate(request);
		
		return ApiResponse.<IntrospectRequest>builder()
		        .result(result)
		        .build();
	}
}
