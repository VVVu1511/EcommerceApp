package com.example.identityservice.configuration;


import java.text.ParseException;
import java.util.Objects;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import org.springframework.beans.factory.annotation.Value;

@Component
public class CustomJwtDecoder implements JwtDecoder{
	@Value("${jwt.signerKey}")
	private String signerKey;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	private NimbusJwtDecoder nimbusJwtDecoder = null;
	
	@Override
	public Jwt decode(String token) throws JwtException{
		try {
			IntrospectResponse response = authenticationService.introspect(IntrospectRequest.builder()
					.token(token)
					.build());
			
			if(!response.isValid())
				throw new JwtException("Invalid token");
				
		} catch (JOSEException | ParseException e) {
			throw new JwtException(e.getMessage());
		}
		
		if(Objects.isNull(nimbusJwtDecoder)) {
			SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), token);
			nimbusJwtDecoder = NimbusJwtDecoder
					.withSecretKey(secretKeySpec)
					.macAlgorithm(MacAlgorithm.HS512)
					.build();
		}
		
		System.out.println(nimbusJwtDecoder);
		
		return nimbusJwtDecoder.decode(token);
		
	}
}
