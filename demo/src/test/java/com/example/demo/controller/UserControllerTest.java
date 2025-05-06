package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;

import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.service.UserService;
import com.example.identityservice.validator.DobConstraints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	private UserCreationRequest request;
	private UserResponse userResponse;
	private LocalDate dob;
	@BeforeEach
	void initData() {
		dob = LocalDate.of(1990, 1, 1);
		
		request = UserCreationRequest.builder()
				.username("john")
				.firstName("David")
				.lastName("Laid")
				.password("12345678")
				.dob(dob)
				.build();
		
		userResponse = UserResponse.builder()
				//need to paste real ID to test
				.id("111")
				.username("john")
				.firstName("David")
				.lastName("Laid")
				.dob(dob)
				.build();
	}
	
	@Test
	void createUser() {
		//GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		String content = new String();
		
		try {
			content = objectMapper.writeValueAsString(request);
		} catch(JsonProcessingException e) {
			
		}
		
		Mockito.when(userService.createUser(ArgumentMatchers.any()))
			.thenReturn(userResponse);
		
		//WHEN, THEN
		try {
			mockMvc.perform(MockMvcRequestBuilders
					.post("/users")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(content))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000)
			);
		} catch(Exception e) {
			
		}
		
		
	}
}
