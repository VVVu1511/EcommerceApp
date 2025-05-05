package com.example.identityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identityservice.entity.InvalidatedToken;
import com.example.identityservice.entity.Permission;


@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String>{
	
}
