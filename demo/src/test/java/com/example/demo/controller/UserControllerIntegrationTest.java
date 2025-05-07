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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.service.UserService;
import com.example.identityservice.validator.DobConstraints;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
@Testcontainers
public class UserControllerIntegrationTest {	
	@Container
	static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:latest");
	
	@DynamicPropertySource
	static void configureDatasource(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
		registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
		registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
	}
	
	@Autowired
	private MockMvc mockMvc;
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
	void createUser_validRequest_success() throws Exception{
		//GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(request);
		
		//WHEN, THEN
		
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
				.andExpect(MockMvcResultMatchers.jsonPath("result.username").value("john"))
				.andExpect(MockMvcResultMatchers.jsonPath("result.firstname").value("David"))
				.andExpect(MockMvcResultMatchers.jsonPath("result.lastname").value("Laid"))
				;
		
		log.info("Result: {}",response.andReturn().getResponse().getContentAsString());
	}
	
	@Test
	void createUser_usernameInvalid_fail() throws Exception{
		//GIVEN
		request.setUsername("joh");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(request);
		 
		//WHEN, THEN
		
//		mockMvc.perform(MockMvcRequestBuilders
//				.post("/users")
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(content))
//				.andExpect(MockMvcResultMatchers.status().isBadRequest())
//				.andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
//				.andExpect(MockMvcResultMatchers.jsonPath("message").value("sss"))
//				;
	}
}
