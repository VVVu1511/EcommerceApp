package com.example.identityservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.mapper.PermissionMapper;
import com.example.identityservice.repository.PermissionRepository;
import com.example.identityservice.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
	PermissionRepository permissionRepository;
	PermissionMapper permissionMapper;
	
	public PermissionResponse create(PermissionRequest request) {
		Permission permission = permissionMapper.toPermission(request);
		
		permission = permissionRepository.save(permission);
		
		return permissionMapper.toPermissionResponse(permission);
	}
	
	public List<PermissionResponse> getAll(){
		List<Permission> permissions = permissionRepository.findAll();
		
		return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
	}
	
	public void delete(String permission) {
		permissionRepository.deleteById(permission);
	}
}
