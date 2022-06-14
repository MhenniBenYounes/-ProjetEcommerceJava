package fr.idprocess.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.idprocess.admin.model.Admin;

@Service
public class AdminSecurityService implements UserDetailsService {

	@Autowired
	private AdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = adminService.findByUsername(username);

	       if (admin != null) {
	            return admin;
	        }
	        throw new UsernameNotFoundException("Identifiant introuvable !");
	}
}
