package fr.idprocess.users.controller;

import static fr.idprocess.common.message.CommonMessage.BAD_PWD;
import static fr.idprocess.common.message.CommonMessage.CONFIRM_PWD;
import static fr.idprocess.common.message.CommonMessage.PROFILE_UPDATED;
import static fr.idprocess.common.message.CommonMessage.USED_MAIL;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.idprocess.users.model.User;
import fr.idprocess.users.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder pwdEncoder;

	// Page de profil utilisateur
	@GetMapping("/myAccount")
	public String displayMyAccount(Model model, User user) {

		model.addAttribute("user", userService.getConnectedUser());
		return "users/registred/myAccount";
	}

	// Formulaire mise à jour profil --> onglet mon profil
	@PostMapping("/updateMyProfile")
	public ModelAndView updateMyProfile(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult)
			throws Exception {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("/users/registred/myAccount");
		}

		if (!userService.getConnectedUser().getEmail().equalsIgnoreCase(user.getEmail())
				&& userService.existsByEmail(user.getEmail())) {
			modelAndView.addObject("usedMail", USED_MAIL.getMessage());
			modelAndView.setViewName("users/registred/myAccount");

		} else if (!user.getPassword().matches(".{8,30}")) {

			modelAndView.addObject("badPwd", BAD_PWD.getMessage());
			modelAndView.setViewName("users/registred/myAccount");

			// Vérification de la confirmation du mot de passe
		} else if (!user.getPassword().equals(user.getConfirmPassword())) {

			modelAndView.addObject("confirmPwd", CONFIRM_PWD.getMessage());
			modelAndView.setViewName("users/registred/myAccount");

		} else {
			userService.getConnectedUser().setGender(user.getGender());
			userService.getConnectedUser().setAdress(user.getAdress());
			userService.getConnectedUser().setCity(user.getCity());
			userService.getConnectedUser().setEmail(user.getEmail());
			userService.getConnectedUser().setFirstName(user.getFirstName());
			userService.getConnectedUser().setLastName(user.getLastName());
			userService.getConnectedUser().setPhoneNumber(user.getPhoneNumber());
			userService.getConnectedUser().setZipCode(user.getZipCode());
			userService.getConnectedUser().setBirthDate(user.getBirthDate());
			userService.getConnectedUser().setPassword(pwdEncoder.encode(user.getPassword()));

			userService.updateUser(userService.getConnectedUser());

			modelAndView.addObject("profileUpdated", PROFILE_UPDATED.getMessage());
			modelAndView.setViewName("users/registred/myAccount");
		}
		return modelAndView;
	}
}
