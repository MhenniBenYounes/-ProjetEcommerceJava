// Javascript coté navigateur pour vérifier la confirmation du mot de passe d'inscription client.

var password = document.getElementById("password"), confirmPassword = document
		.getElementById("confirmPassword");

function validatePassword() {
	if (password.value != confirmPassword.value) {
		confirmPassword
				.setCustomValidity("Veuillez ressaisir votre mot de passe");
	} else {
		confirmPassword.setCustomValidity('');
	}
}

password.onchange = validatePassword;
confirmPassword.onkeyup = validatePassword;