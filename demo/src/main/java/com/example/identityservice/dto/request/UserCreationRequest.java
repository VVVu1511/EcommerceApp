package com.example.identityservice.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreationRequest {
	@Size(min=3,message = "USERNAME_INVALID")
	private String username;
	
	@Size(min=8, message = "PASSWORD_INVALID")
	private String password;
	private String firstName;
	private String lastName;

	private LocalDate dob;
}
