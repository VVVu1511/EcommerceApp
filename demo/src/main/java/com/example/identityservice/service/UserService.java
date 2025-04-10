package com.example.identityservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.mapper.UserMapper;
import com.example.identityservice.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	
	public User createUser(UserCreationRequest request) {
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXIST);
		}
		
		User user = userMapper.toUser(request);
		
		return userRepository.save(user);
	}
	
	public User updateUser(String userId,UserUpdateRequest request) {
		User user = getUser(userId);
		
		user.setPassword(request.getPassword());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setDob(request.getDob());
		
		return userRepository.save(user);
	}
	
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}
	
	public List<User> getUser(){
		return userRepository.findAll();
	}
	
	public User getUser(String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found!"));
	}
}
