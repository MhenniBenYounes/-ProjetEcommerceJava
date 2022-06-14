package fr.idprocess.common.controller;

import static fr.idprocess.common.message.CommonMessage.BAD_LINK;
import static fr.idprocess.common.message.CommonMessage.BAD_MAIL;
import static fr.idprocess.common.message.CommonMessage.BAD_PWD;
import static fr.idprocess.common.message.CommonMessage.COMPLETE_REGISTER;
import static fr.idprocess.common.message.CommonMessage.CONFIRM_PWD;
import static fr.idprocess.common.message.CommonMessage.FORGOT_MAIL_SENT;
import static fr.idprocess.common.message.CommonMessage.PWDRESET_DONE;
import static fr.idprocess.common.message.CommonMessage.REGISTER_DONE;
import static fr.idprocess.common.message.CommonMessage.USED_MAIL;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.idprocess.common.service.EmailService;
import fr.idprocess.common.util.Email;
import fr.idprocess.users.model.Token;
import fr.idprocess.users.model.User;
import fr.idprocess.users.service.TokenService;
import fr.idprocess.users.service.UserService;

@Controller
public class CommonRegisterController {

	/* Partie connexion pour Admin (pas connectés) */
	@RequestMapping("/adminLogin")
	public String adminLogin(SecurityContextHolderAwareRequestWrapper secuRequestWrapper) {

		// Empêcher un Admin connecté à se reconnecter une autre fois sans logout
		boolean isAdminConnected = secuRequestWrapper.isUserInRole("ROLE_ADMIN");

		if (isAdminConnected)/* par défaut sur true */ {
			return "redirect:/admin/";
		}
		return "admin/login";
	}
	/* Partie connexion pour Admin (pas connectés) */

	/* *****************************************************************************************/
	/* *****************************************************************************************/

	/* Partie authentification et inscription pour les visiteurs (pas connectés) */

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	Email email;

	@Autowired
	EmailService emailService;

	@Autowired
	private PasswordEncoder pwdEncoder;

	// Empêcher un client connecté de se reconnecter une autre fois sans logout
	@RequestMapping("/login")
	public String login(Model model, SecurityContextHolderAwareRequestWrapper secuRequestWrapper) {

		// Stocker l'état de la connexion appartenant à un objet de role USER
		// dans une variable boolean, pour savoir l'état de connexion
		boolean isUserConnected = secuRequestWrapper.isUserInRole("ROLE_USER");

		if (isUserConnected) {
			return "redirect:/user/myAccount";
		}

		return "users/public/login";
	}

	@GetMapping("/register")
	public String registerPage(User user, SecurityContextHolderAwareRequestWrapper secuRequestWrapper) {

		// Empêcher un client connecté à s'enregistrer avant déconnexion
		boolean isUserConnected = secuRequestWrapper.isUserInRole("ROLE_USER");

		if (isUserConnected) {
			return "redirect:/user/myAccount";
		}
		return "users/public/register";
	}

	// Mapping URL pour enregistrer les nouveaux clients
	@PostMapping("/register")
	public ModelAndView registerUser(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult,
			HttpServletRequest request) throws MessagingException {

		// Vérification des erreurs sur les champs définis avec javax.validation
		if (bindingResult.hasErrors()) {
			return new ModelAndView("users/public/register");
		}

		// Vérification d'un email existant, éviter les doublons
		boolean dbExistingUser = userService.existsByEmail(user.getEmail());

		if (dbExistingUser) {
			modelAndView.addObject("usedMail", USED_MAIL.getMessage());
			modelAndView.setViewName("users/public/register");
			// Vérification du pattern du mot de passe, entre 8 et 30 char
		} else if (!user.getPassword().matches(".{8,30}")) {

			modelAndView.addObject("badPwd", BAD_PWD.getMessage());
			modelAndView.setViewName("users/public/register");

			// Vérification de la confirmation du mot de passe
		} else if (!user.getPassword().equals(user.getConfirmPassword())) {

			modelAndView.addObject("confirmPwd", CONFIRM_PWD.getMessage());
			modelAndView.setViewName("users/public/register");
		} else {
			// Tous les données sont valides, préparation du jeton de confirmation
			Token token = new Token(user);

			/* Préparation de l'email à envoyer pour la confirmation avec jeton */
			email.setTo(user.getEmail());
			email.setSubject("BricoPasCher--Inscription à compléter");
			// Template Thymeleaf pour l'envoie d'email
			email.setTemplate("common/email/confirmAccount");

			// Varaible de contexte url
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/";

			// Préparation des variables pour le template thymeleaf
			Map<String, Object> model = new HashMap<>();
			model.put("userIdentity", user.getGender() + " " + user.getLastName() + " " + user.getFirstName());
			model.put("appUrl", appUrl);
			model.put("urlToken", "confirm-account?token=");
			model.put("token", token.getToken());
			email.setModel(model);
			// Envoie d'email
			emailService.sendEmail(email);

			// Validation des données avec les setters pour l'enregistrement
			user.setGender(user.getGender());
			user.setLastName(user.getLastName());
			user.setFirstName(user.getFirstName());
			user.setEmail(user.getEmail());
			user.setPassword(pwdEncoder.encode(user.getPassword()));

			// Enregistrement de l'objet user dans la table clients
			userService.createUser(user);

			// Enregistrement du jeton pour user(many to one) dans la table jetons
			tokenService.createToken(token);

			// Envoie du message de la félicitaion de l'enregistrement
			modelAndView.addObject("completeRegister", COMPLETE_REGISTER.getMessage());
			modelAndView.setViewName("users/public/register");
		}
		return modelAndView;
	}

	@RequestMapping("/confirm-account")
	public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		Token token = tokenService.findByToken(confirmationToken);

		if (token != null) {
			User user = userService.findByEmail(token.getUser().getEmail());
			if (user.getActivationDate() == null) {
				// activer le compte utilisateur
				user.setEnabled(true);
				// activer la date de création du client
				user.setActivationDate(LocalDate.now());
				userService.updateUser(user);
				modelAndView.addObject("registerDone", REGISTER_DONE.getMessage());
				modelAndView.setViewName("users/public/login");
			} else {
				modelAndView.addObject("badLink", BAD_LINK.getMessage());
				modelAndView.setViewName("users/public/login");
			}
		} else {
			modelAndView.addObject("badLink", BAD_LINK.getMessage());
			modelAndView.setViewName("users/public/login");
		}

		return modelAndView;
	}

	// Mapping pour URL ( /forgot-password )
	@GetMapping("/forgot-password")
	public String ResetPasswordPage(User user, SecurityContextHolderAwareRequestWrapper secuRequestWrapper) {

		// Empêcher un client connecté à récupérer un mot de passe perdu
		boolean isUserConnected = secuRequestWrapper.isUserInRole("ROLE_USER");

		if (isUserConnected) {
			return "redirect:/user/myAccount";
		}
		return "users/public/pwdForgot";
	}

	@PostMapping("/forgot-password")
	public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user, HttpServletRequest request)
			throws MessagingException {
		User existingUser = userService.findByEmail(user.getEmail());
		if (existingUser != null) {
			// Création du jeton pour le client enregistré
			Token confirmationToken = new Token(existingUser);

			// Création du jeton pour la réinitialisation du mot de passe
			tokenService.createToken(confirmationToken);

			/* Préparation de l'email à envoyer pour la confirmation avec jeton */

			// Varaible de contexte url
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/";

			email.setTo(existingUser.getEmail());
			email.setSubject("Brico Pas Cher--Réinitialisation du mot de passe à compléter");
			email.setTemplate("common/email/resetPwdLink");

			Map<String, Object> model = new HashMap<>();
			model.put("userIdentity",
					existingUser.getGender() + " " + existingUser.getLastName() + " " + existingUser.getFirstName());
			model.put("appUrl", appUrl);
			model.put("urlToken", "confirm-reset?token=");
			model.put("token", confirmationToken.getToken());
			email.setModel(model);

			emailService.sendEmail(email);

			modelAndView.addObject("forgotMailSent", FORGOT_MAIL_SENT.getMessage());
			modelAndView.setViewName("users/public/pwdForgot");

		} else {
			modelAndView.addObject("badMail", BAD_MAIL.getMessage());
			modelAndView.setViewName("users/public/pwdForgot");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/confirm-reset", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
		Token token = tokenService.findByToken(confirmationToken);

		// Partie Get
		if (token != null) {
			User user = userService.findByEmail(token.getUser().getEmail());
			user.setEnabled(true);
			userService.updateUser(user);
			modelAndView.addObject("user", user);
			modelAndView.addObject("email", user.getEmail());
			modelAndView.setViewName("users/registred/pwdReset");
			// Partie Post
		} else {
			modelAndView.addObject("badLink", BAD_LINK.getMessage());
			modelAndView.setViewName("users/registred/pwdReset");
		}

		return modelAndView;
	}

	@PostMapping("/reset-password")
	public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {

		if (user.getEmail() != null) {
			// use email to find user
			User tokenUser = userService.findByEmail(user.getEmail());
			tokenUser.setEnabled(true);
			tokenUser.setPassword(pwdEncoder.encode(user.getPassword()));
			userService.updateUser(tokenUser);
			modelAndView.addObject("pwdResetDone", PWDRESET_DONE.getMessage());
			modelAndView.setViewName("users/public/login");
		} else {
			modelAndView.addObject("badLink", BAD_LINK.getMessage());
			modelAndView.setViewName("users/registred/pwdReset");
		}

		return modelAndView;
	}
	/* Partie authentification et inscription pour les visiteurs (pas connectés) */
}
