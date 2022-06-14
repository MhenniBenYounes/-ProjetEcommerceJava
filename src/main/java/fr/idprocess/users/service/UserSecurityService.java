package fr.idprocess.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.idprocess.users.model.User;

@Service
public class UserSecurityService implements UserDetailsService {

	@Autowired
	private UserService userService;

	/*
	 * chargement du paramètre de la méthode ( loadUserByUsername ) pour Spring
	 * Security par email au lieu de username qui est par défaut. Pour charger le
	 * mécanisme d'authentification par e-mail et non par username de user.
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.findByEmail(email);

	       if (user != null) {
	            return user;
	        }
	        throw new UsernameNotFoundException("Identifiant introuvable !");
	}
}
