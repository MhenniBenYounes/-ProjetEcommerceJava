package fr.idprocess.common.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import fr.idprocess.common.service.ProductService;

public class UniqueCodeBarreValidator implements ConstraintValidator<UniqueCodeBarre, String> {

	@Autowired
	ProductService productService;

	@Override
	public boolean isValid(String codebarre, ConstraintValidatorContext cxt) {
		return codebarre != null && !codebarreExist(codebarre);
	}
	
	private boolean codebarreExist(String codebarre) {
		return productService.existsCodeBarreByBarcode(codebarre);
	}

}