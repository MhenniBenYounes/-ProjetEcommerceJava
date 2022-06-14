package fr.idprocess.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class XssJsoupSafeValidator implements ConstraintValidator<XssJsoupSafe, String> {

	// Contrer les attaques XSS avec API Jsoup en nettoyant les entrées des champs
	// html et leurs balises et surtout vider tout ce qui est javascript avant de
	// les enregistrer dans la BD.
	// ex : <p>A</p> ---> devient <p>A</p> ** ex : <html>m</html> ---> devient m
	// ex : <script>alert'a'</script> ----> devient empty (vide)
	// ex : <style>body{color:red;}</style> devient empty (vide)

	// En plus Vérification de @NotNull, @NotBlank, @NotEmpty et les doubles espaces
	// inutiles.

	@Override
	public boolean isValid(String dataString, ConstraintValidatorContext cxt) {
		if (dataString == null) {
			return true;
		} else if (Jsoup.clean(dataString, Whitelist.basic()).replaceAll("&amp;", "&").equalsIgnoreCase(dataString)) {
			return true;
		}
		return false;
	}
}