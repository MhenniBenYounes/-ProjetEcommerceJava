package fr.idprocess.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

	// Page panier client
	@GetMapping("/cart")
	public String displayCart() {
		return "users/public/cart";
	}
}
