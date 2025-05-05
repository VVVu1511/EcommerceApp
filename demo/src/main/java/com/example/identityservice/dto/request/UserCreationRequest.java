package com.example.identityservice.dto.request;

import java.time.LocalDate;

import com.example.identityservice.validator.DobConstraints;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserCreationRequest {
	@Size(min=4,message = "USERNAME_INVALID")
	String username;
	
	@Size(min=9, message = "PASSWORD_INVALID")
	String password;
	String firstName;
	String lastName;

	@DobConstraints(min = 18, message = "INVALID_DOB")
	LocalDate dob;
}
