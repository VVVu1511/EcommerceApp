package com.example.identityservice.service;

import java.beans.IntrospectionException;
import java.security.PrivateKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.hibernate.usertype.LoggableUserType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.identityservice.dto.request.AuthenticationRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.AuthenticationResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.entity.User;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
	UserRepository userRepository;
	
	@NonFinal
	@Value("${jwt.signerKey}")
	protected String SIGNER_KEY;
			
	
	public IntrospectResponse introspect(IntrospectRequest request)
			throws JOSEException, ParseException{
		String token = request.getToken();
		
		JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
		
		SignedJWT signedJWT = SignedJWT.parse(token);
		
		Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
		
		boolean verified = signedJWT.verify(verifier);
		
		return IntrospectResponse.builder()
				.valid(verified && expityTime.after(new Date()))				
				.build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
				
		boolean authenticated = passwordEncoder.matches(request.getPassword(), 
					user.getPassword());
		
		if(!authenticated) {
			throw new AppException(ErrorCode.UNAUTHENTICATED);
		}
		
		String tokenString = generateToken(request.getUsername());
		
		
		return AuthenticationResponse.builder()
				.token(tokenString)
				.authenticated(true)
				.build();
	}
	
	private String generateToken(String username) {
		JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
		
		
		
		JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
				.subject(username)
				.issuer("Vu.com")
				.issueTime(new Date())
				.expirationTime(new Date(
						Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
				))
				.claim("userId", "Custom")
				.build();
		
		Payload payload = new Payload(jwtClaimsSet.toJSONObject());
		
		JWSObject jwsObject = new JWSObject(header, payload);
		
		try {
			jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
			return jwsObject.serialize();
		} catch(JOSEException e) {
			throw new RuntimeException(e);
		}
	}
}
