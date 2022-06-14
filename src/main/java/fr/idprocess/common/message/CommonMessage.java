package fr.idprocess.common.message;

public enum CommonMessage {

	// Message partie inscription, mot de passe oublié, mon profil --> Controller
	USED_MAIL("Cette adresse email est déjà utilisée."),
	BAD_PWD("Veuillez saisir un mot de passe entre 8 et 30 caractères."),
	CONFIRM_PWD("Veuillez confirmer votre mot de passe."),
	REGISTER_DONE("Félicitation vous pouvez dès à présent vous connecter à votre espace personnel."),
	BAD_LINK("Ce lien n'est plus valide ou inexistant!"),
	FORGOT_MAIL_SENT("Un email vous a été envoyé afin de réinitialiser votre mot de passe."),
	BAD_MAIL("Adresse e-mail inexistante."), 
	PWDRESET_DONE("Votre mot de passe a été réinitialisé avec succès."),
	COMPLETE_REGISTER("Un email vous a été envoyé afin de valider votre inscription."),
	PROFILE_UPDATED("Votre profil est maintenant à jour");

	private final String message;

	private CommonMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
