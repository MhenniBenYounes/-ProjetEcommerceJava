package fr.idprocess.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping("/")
	public String adminHomePage() {
		return "admin/index";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "admin/dashBoard";
	}
	
	@GetMapping("/stuff")
	public String stuff() {
		return "admin/stuff";
	}
}
