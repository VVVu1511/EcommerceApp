package com.example.identityservice.controller;


import java.lang.System.Logger;
import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.request.LogoutRequest;
import com.example.identityservice.dto.request.RefreshRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class AuthenticationController {
	AuthenticationService authenticationService;
	
	@PostMapping("/token")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		AuthenticationResponse result = authenticationService.authenticate(request);
		
		return ApiResponse.<AuthenticationResponse>builder()
		        .result(result)
		        .build();
	}
	
	@PostMapping("/introspect")
	ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
		throws ParseException, JOSEException{
		IntrospectResponse result = authenticationService.introspect(request);
		
		return ApiResponse.<IntrospectResponse>builder()
		        .result(result)
		        .build();
	}
	
	@PostMapping("/logout")
	ApiResponse<Void> logout(@RequestBody LogoutRequest request)
		throws ParseException, JOSEException{
		authenticationService.logout(request);
		
		return ApiResponse.<Void>builder()
		        .build();
	}
	
	@PostMapping("/refresh")
	ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request)
	 throws ParseException, JOSEException{
		AuthenticationResponse result = authenticationService.refreshToken(request);
		
		return ApiResponse.<AuthenticationResponse>builder()
		        .result(result)
		        .build();
	}
}
