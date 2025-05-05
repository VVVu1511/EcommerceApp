package com.example.identityservice.dto.request;

import java.time.LocalDate;
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
	@Size(min=3,message = "USERNAME_INVALID")
	String username;
	
	@Size(min=8, message = "PASSWORD_INVALID")
	String password;
	String firstName;
	String lastName;

	
	LocalDate dob;
}
