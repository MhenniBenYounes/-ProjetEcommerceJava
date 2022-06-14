package fr.idprocess.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticCommonController {
	
	// Page de formulaire de devis gratuit
	@GetMapping("/confirmAccount")
	public String test() {
		return "common/email/confirmAccount";
	}

	// Page d'accueil principale
	@GetMapping("/")
	public String userHomePage() {
		return "users/public/index";
	}

	// Page de formulaire de devis gratuit
	@GetMapping("/quote")
	public String devis() {
		return "users/public/quote";
	}

	// Page de formulaire de contact
	@GetMapping("/contact")
	public String contact() {
		return "users/public/contact";
	}

	// Page sur la société
	@GetMapping("/about")
	public String about() {
		return "users/public/about";
	}

	// Page sur les Condition Générale de Ventes
	@GetMapping("/cgv")
	public String cgv() {
		return "users/public/cgv";
	}

	// Page sur la politique de livraison
	@GetMapping("/deliveryTerms")
	public String deliveryTerms() {
		return "users/public/deliveryTerms";
	}

	// Page info sur la politique des garanties sur les articles
	@GetMapping("/warrantyTerms")
	public String warrantyTerms() {
		return "users/public/warrantyTerms";
	}
}
