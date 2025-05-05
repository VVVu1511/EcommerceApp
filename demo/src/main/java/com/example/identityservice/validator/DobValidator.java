package com.example.identityservice.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DobValidator implements ConstraintValidator<DobConstraints, LocalDate> {

	private int min;
	
	@Override
	public void initialize(DobConstraints constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
		min = constraintAnnotation.min();
	}

	@Override
	public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
		if(Objects.isNull(value))
			return true;
		
		long years = ChronoUnit.YEARS.between(value, LocalDate.now()); 
		
		return years >= min;
	}
	
}
