package fr.idprocess.common.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/* Classe Controller qui va gérer les différentes erreurs survenues de la navigation sur le site 
   pour les administrateurs, utilisateurs enregistrés et visiteurs. */
@Controller
public class CommonErrorController implements ErrorController {

	@RequestMapping("/error")
	public String errorManagement(HttpServletRequest request) {

		/*
		 * Enregistrer les codes erreurs http du org.springframework.http dans
		 * httpStatus après réception de la demande du request
		 */
		int httpstatus = Integer.parseInt(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString());

		// Afficher la page d'erreur source url non trouvée
		if (httpstatus == HttpStatus.NOT_FOUND.value()) {
			return "common/error/404Error";
			// Afficher la page d'erreur Bad Request (Syntaxe, token manquant)
		} else if (httpstatus == HttpStatus.BAD_REQUEST.value()) {
			return "common/error/400Error";
			// Afficher la page d'erreur Bad method (méthode non configurée)
		} else if (httpstatus == HttpStatus.METHOD_NOT_ALLOWED.value()) {
			return "common/error/405Error";
			// Afficher la page d'erreur problème fatale du serveur
		} else if (httpstatus == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return "common/error/500Error";
		}
		// Afficher la page d'erreur générique pour le reste
		return "common/error/genericError";
	}

	/*
	 * Même sans implémenter la classe ErrorController, SpringBoot gèrera
	 * bien @RequestMapping("/error") comme gestionnaire d'erreur en mettant cette
	 * dernière annotation pour la classe elle même!!
	 */
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return null;
	}
}