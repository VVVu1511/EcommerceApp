package com.example.identityservice.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.enums.Role;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
	UserRepository userRepository;
	UserMapper userMapper;
	
	public UserResponse createUser(UserCreationRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXIST);
		}
		
		User user = userMapper.toUser(request);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		HashSet<String> roles = new HashSet<>();
		roles.add(Role.USER.name());
		
		user.setRoles(roles);
		
		return userMapper.toUserResponse(userRepository.save(user));
	}
	
	public UserResponse updateUser(String userId,UserUpdateRequest request) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		userMapper.updateUser(user, request);
		userRepository.save(user);
		
		return userMapper.toUserResponse(user);
	}
	
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
	
	public List<User> getUser(){
		return userRepository.findAll();
	}
	
	public UserResponse getUser(String id) {
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found!"))
				);
	}
}
