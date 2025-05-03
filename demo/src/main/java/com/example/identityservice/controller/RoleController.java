package com.example.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identityservice.dto.request.ApiResponse;
import com.example.identityservice.dto.request.PermissionRequest;
import com.example.identityservice.dto.request.RoleRequest;
import com.example.identityservice.dto.response.PermissionResponse;
import com.example.identityservice.dto.response.RoleResponse;
import com.example.identityservice.service.PermissionService;
import com.example.identityservice.service.RoleService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class RoleController {
	RoleService roleService;
	
	@PostMapping
	ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
		return ApiResponse.<RoleResponse>builder()
				.result(roleService.create(request))
				.build();
	}
	
	@GetMapping
	ApiResponse<List<RoleResponse>> getAll(){
		return ApiResponse.<List<RoleResponse>>builder()
				.result(roleService.getAll())
				.build();
	}
	
	@DeleteMapping("/{role}")
	ApiResponse<Void> delete(@PathVariable String role){
		roleService.delete(role);
		return ApiResponse.<Void>builder().build();
	}
	
}
