package fr.idprocess.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import fr.idprocess.users.model.User;
import fr.idprocess.users.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	// Méthode pour rendre le client connecté à travers la ressource
	@Override
	public User getConnectedUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User connectedUser = (User) authentication.getPrincipal();

		return connectedUser;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void createUser(User user) {
		User localUser = userRepository.findByEmail(user.getEmail());

		if (localUser != null) {
			System.out.println("Erreur le client :  " + user.getEmail() + " est déjà existant!!");
		} else {
			localUser = userRepository.save(user);
		}
	}

	@Override
	public void updateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}
}
