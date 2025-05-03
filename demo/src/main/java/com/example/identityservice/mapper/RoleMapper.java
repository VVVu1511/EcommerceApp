package com.example.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.request.UserCreationRequest;
import com.example.identityservice.dto.request.UserUpdateRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.RoleResponse;
import com.example.identityservice.dto.response.UserResponse;
import com.example.identityservice.entity.Permission;
import com.example.identityservice.entity.Role;
import com.example.identityservice.entity.User;

@Mapper(
	    componentModel = "spring"
)

public interface RoleMapper {
	
	@Mapping(target = "permissions", ignore = true)	
	Role toRole(RoleRequest request);
	
	RoleResponse toRoleResponse(Role role);
}
