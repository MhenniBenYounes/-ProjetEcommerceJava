package fr.idprocess.common.validator;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValueValidator implements ConstraintValidator<GenderValue, String> {

	public static final String[] GENRE = { "Monsieur", "Madame", "Société" };

	@Override
	public boolean isValid(String gender, ConstraintValidatorContext cxt) {
		return gender != null && Arrays.asList(GENRE).contains(gender);
	}

}